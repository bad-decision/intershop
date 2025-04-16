package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.service.CacheItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheItemServiceImpl implements CacheItemService {

    private final ItemRepository itemRepository;

    @Override
    @Cacheable(value = ITEMS_CACHE, key = "#itemId")
    public Mono<ItemEntity> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    @Cacheable(value = ITEMS_LIST_CACHE, key = "#itemIds")
    public Mono<List<ItemEntity>> findByIds(List<Long> itemIds) {
        return itemRepository.findByIds(itemIds).collectList();
    }

    @Override
    @Cacheable(value = ITEMS_PAGE_CACHE, key = "{#search, #pageable}")
    public Mono<Page<ItemEntity>> searchItems(String search, Pageable pageable) {
        Flux<ItemEntity> items = itemRepository.filterItems(search, pageable);
        Mono<Long> totalCount = itemRepository.countFilteredItems(search);
        return Mono.zip(items.collectList(), totalCount, (content, total) -> new PageImpl<>(content, pageable, total));
    }

    @Override
    @CacheEvict(cacheNames = {ITEMS_PAGE_CACHE, ITEMS_LIST_CACHE}, allEntries = true)
    public Mono<Boolean> evictItemsListCache() {
        return Mono.just(true);
    }
}