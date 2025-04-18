package ru.azmeev.intershop.showcase.web.mapper;

import org.springframework.stereotype.Component;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.web.dto.ItemAddDto;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDto toItemDto(ItemEntity item, CartItemEntity cartItem) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .imgPath(item.getImgPath())
                .price(item.getPrice())
                .count(cartItem != null ? cartItem.getCount() : 0)
                .build();
    }

    public List<ItemDto> toItemDto(List<ItemEntity> items, List<CartItemEntity> cartItems) {
        return items.stream()
                .map(item -> {
                    CartItemEntity cartItem = cartItems.stream()
                            .filter(x -> x.getItemId().equals(item.getId()))
                            .findFirst()
                            .orElse(null);
                    return toItemDto(item, cartItem);
                }).toList();
    }

    public List<ItemEntity> toItemEntity(List<ItemAddDto> items) {
        return items.stream()
                .map(this::toItemEntity)
                .toList();
    }

    public ItemEntity toItemEntity(ItemAddDto item) {
        ItemEntity entity = new ItemEntity();
        entity.setDescription(item.getDescription());
        entity.setPrice(item.getPrice());
        entity.setTitle(item.getTitle());
        entity.setImgPath(item.getImgPath());
        return entity;
    }
}
