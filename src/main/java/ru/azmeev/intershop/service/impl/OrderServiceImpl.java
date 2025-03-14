package ru.azmeev.intershop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.entity.OrderItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.OrderItemRepository;
import ru.azmeev.intershop.repository.OrderRepository;
import ru.azmeev.intershop.service.OrderService;
import ru.azmeev.intershop.web.dto.OrderDto;
import ru.azmeev.intershop.web.mapper.OrderItemMapper;
import ru.azmeev.intershop.web.mapper.OrderMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderEntity createOrder() {
        OrderEntity order = new OrderEntity();
        List<CartItemEntity> cartItems = cartItemRepository.getCartItems();
        List<OrderItemEntity> orderItems = cartItems.stream()
                .map(cartItem -> orderItemMapper.toOrderItemEntity(cartItem, order))
                .toList();
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);
        return orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Error while creating order"));
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    public OrderDto getOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Item with id %s not found", id)));
        return orderMapper.toOrderDto(order);
    }
}
