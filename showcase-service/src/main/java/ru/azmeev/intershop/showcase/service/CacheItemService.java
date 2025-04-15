package ru.azmeev.intershop.showcase.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;

import java.util.List;

public interface CacheItemService {

    String ITEMS_CACHE = "items";
    String ITEMS_LIST_CACHE = "itemsList";
    String ITEMS_PAGE_CACHE = "itemsPage";

    Mono<ItemEntity> findById(Long itemId);

    Mono<Page<ItemEntity>> searchItems(String search, Pageable pageable);

    Mono<List<ItemEntity>> findByIds(List<Long> itemIds);
}
