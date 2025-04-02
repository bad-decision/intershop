package ru.azmeev.intershop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.entity.BaseEntity;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.model.enums.SortType;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
import ru.azmeev.intershop.service.ItemService;
import ru.azmeev.intershop.web.dto.ItemDto;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.web.mapper.ItemMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemMapper itemMapper;

    @Override
    public Mono<ItemDto> getItem(Long itemId) {
        return itemRepository.findById(itemId)
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

        Flux<ItemEntity> items = itemRepository.filterItems(filter.getSearch(), pageable);
        Mono<Long> totalCount = itemRepository.countFilteredItems(filter.getSearch());

        return Mono.zip(items.collectList(), totalCount)
                .flatMap(tuple -> {
                    List<ItemEntity> content = tuple.getT1();
                    Long total = tuple.getT2();
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
