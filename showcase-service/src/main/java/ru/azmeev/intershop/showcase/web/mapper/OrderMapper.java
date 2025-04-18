package ru.azmeev.intershop.showcase.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;
import ru.azmeev.intershop.showcase.model.entity.OrderItemEntity;
import ru.azmeev.intershop.showcase.web.dto.OrderDto;
import ru.azmeev.intershop.showcase.web.dto.OrderItemDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderDto toOrderDto(OrderEntity order, List<OrderItemEntity> items) {
        List<OrderItemDto> orderItems = items.stream()
                .map(orderItemMapper::toOrderItemDto)
                .toList();
        Double totalSum = orderItems.stream()
                .map(orderItem -> orderItem.getPrice() * orderItem.getCount())
                .reduce(0.0, Double::sum);
        return OrderDto.builder()
                .id(order.getId())
                .orderItems(orderItems)
                .totalSum(totalSum)
                .build();
    }
}
