package ru.azmeev.intershop.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.web.dto.OrderDto;

import java.util.List;

public interface OrderService {

    Mono<OrderEntity> createOrder();

    Mono<List<OrderDto>> getOrders();

    Mono<OrderDto> getOrder(Long id);
}
