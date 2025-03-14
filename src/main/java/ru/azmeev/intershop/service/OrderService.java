package ru.azmeev.intershop.service;

import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.web.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderEntity createOrder();

    List<OrderDto> getOrders();

    OrderDto getOrder(Long id);
}
