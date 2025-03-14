package ru.azmeev.intershop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.model.enums.SortType;
import ru.azmeev.intershop.model.entity.BaseEntity;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
import ru.azmeev.intershop.service.ItemService;
import ru.azmeev.intershop.web.dto.ItemDto;
import ru.azmeev.intershop.web.mapper.ItemMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto getItem(Long itemId) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Item with id %s not found", itemId)));
        CartItemEntity cartItem = cartItemRepository.findByItem(itemId).orElse(null);
        return itemMapper.toItemDto(item, cartItem);
    }

    @Override
    public Page<ItemDto> searchItems(ItemFilterDto filter) {
        Pageable pageable = PageRequest.of(filter.getPageNumber() - 1, filter.getPageSize(), getSort(filter.getSort()));
        Page<ItemEntity> page = itemRepository.filterItems(filter.getSearch(), pageable);
        List<Long> itemIds = page.getContent().stream()
                .map(BaseEntity::getId)
                .toList();
        List<CartItemEntity> cartItems = cartItemRepository.findByItems(itemIds);
        List<ItemDto> itemDtoList = page.getContent().stream()
                .map(item -> {
                    CartItemEntity cartItem = cartItems.stream()
                            .filter(x -> x.getItem().getId().equals(item.getId()))
                            .findFirst()
                            .orElse(null);
                    return itemMapper.toItemDto(item, cartItem);
                }).toList();
        return new PageImpl<>(itemDtoList, page.getPageable(), page.getTotalElements());
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
}
