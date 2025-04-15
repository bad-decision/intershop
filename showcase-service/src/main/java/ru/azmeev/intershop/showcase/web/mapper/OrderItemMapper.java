package ru.azmeev.intershop.showcase.web.mapper;

import org.springframework.stereotype.Component;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.model.entity.OrderItemEntity;
import ru.azmeev.intershop.showcase.web.dto.OrderItemDto;

import java.util.List;
import java.util.Objects;

@Component
public class OrderItemMapper {
    public OrderItemDto toOrderItemDto(OrderItemEntity orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getItemId())
                .title(orderItem.getTitle())
                .description(orderItem.getDescription())
                .imgPath(orderItem.getImgPath())
                .price(orderItem.getPrice())
                .count(orderItem.getCount())
                .build();
    }

    public List<OrderItemEntity> toOrderItemEntities(List<ItemEntity> items, List<CartItemEntity> cartItems, Long orderId) {
        return cartItems.stream()
                .map(cartItem -> {
                    ItemEntity item = items.stream()
                            .filter(x -> x.getId().equals(cartItem.getItemId()))
                            .findFirst()
                            .orElse(null);
                    if (item == null) return null;

                    return OrderItemEntity.builder()
                            .orderId(orderId)
                            .itemId(item.getId())
                            .title(item.getTitle())
                            .description(item.getDescription())
                            .price(item.getPrice())
                            .imgPath(item.getImgPath())
                            .count(cartItem.getCount())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
