package ru.azmeev.intershop.showcase.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;
import ru.azmeev.intershop.showcase.web.dto.OrderDto;

import java.util.List;

public interface OrderService {

    Mono<OrderEntity> createOrder();

    Mono<List<OrderDto>> getOrders();

    Mono<OrderDto> getOrder(Long id);
}
