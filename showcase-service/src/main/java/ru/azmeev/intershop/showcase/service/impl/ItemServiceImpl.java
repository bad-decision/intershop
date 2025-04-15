package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.BaseEntity;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.model.enums.SortType;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.service.CacheItemService;
import ru.azmeev.intershop.showcase.service.ItemService;
import ru.azmeev.intershop.showcase.web.dto.ItemAddDto;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;
import ru.azmeev.intershop.showcase.web.dto.ItemFilterDto;
import ru.azmeev.intershop.showcase.web.mapper.ItemMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final CacheItemService cacheItemService;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public Flux<ItemDto> addItems(List<ItemAddDto> items) {
        return itemRepository.saveAll(itemMapper.toItemEntity(items))
                .map(itemEntity -> itemMapper.toItemDto(itemEntity, null));
    }

    @Override
    public Mono<ItemDto> getItem(Long itemId) {
        return cacheItemService.findById(itemId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("Item with id %s not found", itemId))))
                .flatMap(item -> cartItemRepository.findByItem(itemId)
                        .map(cartItem -> itemMapper.toItemDto(item, cartItem))
                        .defaultIfEmpty(itemMapper.toItemDto(item, null)));
    }

    @Override
    public Mono<Page<ItemDto>> searchItems(ItemFilterDto filter) {
        Pageable pageable = PageRequest.of(
                filter.getPageNumber() - 1,
                filter.getPageSize(),
                getSort(filter.getSort()));

        return cacheItemService.searchItems(filter.getSearch(), pageable)
                .flatMap(page -> {
                    List<ItemEntity> content = page.getContent();
                    Long total = page.getTotalElements();
                    List<Long> itemIds = content.stream()
                            .map(BaseEntity::getId)
                            .toList();
                    return itemIds.size() == 0 ?
                            Mono.just(mapToPage(new ArrayList<>(), pageable, content, total)) :
                            cartItemRepository.findByItems(itemIds)
                                    .collectList()
                                    .map(cartItems -> mapToPage(cartItems, pageable, content, total));
                });
    }

    private Sort getSort(SortType sortType) {
        if (sortType == null)
            return Sort.unsorted();

        return switch (sortType) {
            case NO -> Sort.unsorted();
            case ALPHA -> Sort.by("title");
            case PRICE -> Sort.by("price");
        };
    }

    private Page<ItemDto> mapToPage(List<CartItemEntity> cartItems,
                                    Pageable pageable,
                                    List<ItemEntity> content,
                                    Long total) {
        List<ItemDto> itemDtoList = content.stream()
                .map(item -> {
                    CartItemEntity cartItem = cartItems.stream()
                            .filter(x -> x.getItemId().equals(item.getId()))
                            .findFirst()
                            .orElse(null);
                    return itemMapper.toItemDto(item, cartItem);
                }).toList();
        return new PageImpl<>(
                itemDtoList,
                pageable,
                total
        );
    }
}
