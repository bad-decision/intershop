package ru.azmeev.intershop.web.mapper;

import org.springframework.stereotype.Component;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.entity.OrderItemEntity;
import ru.azmeev.intershop.web.dto.OrderItemDto;

@Component
public class OrderItemMapper {
    public OrderItemDto toOrderItemDto(OrderItemEntity orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .title(orderItem.getTitle())
                .description(orderItem.getDescription())
                .imgPath(orderItem.getImgPath())
                .price(orderItem.getPrice())
                .count(orderItem.getCount())
                .build();
    }

    public OrderItemEntity toOrderItemEntity(CartItemEntity cartItem, OrderEntity order) {
        ItemEntity item = cartItem.getItem();
        return OrderItemEntity.builder()
                .order(order)
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .imgPath(item.getImgPath())
                .count(cartItem.getCount())
                .build();
    }
}
