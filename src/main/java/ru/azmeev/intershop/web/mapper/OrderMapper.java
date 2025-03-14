package ru.azmeev.intershop.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.web.dto.OrderDto;
import ru.azmeev.intershop.web.dto.OrderItemDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderDto toOrderDto(OrderEntity order) {
        List<OrderItemDto> orderItems = order.getOrderItems().stream()
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
