package ru.azmeev.intershop.web.mapper;

import org.springframework.stereotype.Component;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.web.dto.ItemDto;

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
}
