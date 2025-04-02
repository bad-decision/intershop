package ru.azmeev.intershop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.entity.OrderItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
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
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public Mono<OrderEntity> createOrder() {
        return cartItemRepository.getCartItems()
                .collectList()
                .flatMap(cartItems -> {
                    List<Long> itemIds = cartItems.stream().map(CartItemEntity::getItemId).toList();
                    return itemRepository.findByIds(itemIds)
                            .collectList()
                            .flatMap(items -> {
                                OrderEntity order = new OrderEntity();
                                return orderRepository.save(order)
                                        .flatMap(savedOrder -> {
                                            order.setId(savedOrder.getId());
                                            List<OrderItemEntity> orderItems = orderItemMapper.toOrderItemEntities(items, cartItems, savedOrder.getId());
                                            return orderItemRepository.saveAll(orderItems).collectList();
                                        })
                                        .then(cartItemRepository.deleteAll(cartItems))
                                        .thenReturn(order);
                            });
                });
    }

    @Override
    public Mono<List<OrderDto>> getOrders() {
        Mono<List<OrderEntity>> ordersMono = orderRepository.findAll().collectList();
        Mono<List<OrderItemEntity>> orderItemsMono = orderItemRepository.findAll().collectList();
        return Mono.zip(ordersMono, orderItemsMono, (orders, ordersItems) -> orders.stream()
                .map(order -> {
                    List<OrderItemEntity> orderItems = ordersItems.stream().filter(x -> x.getOrderId().equals(order.getId())).toList();
                    return orderMapper.toOrderDto(order, orderItems);
                })
                .toList());
    }

    @Override
    public Mono<OrderDto> getOrder(Long id) {
        Mono<OrderEntity> orderMono = orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("Item with id %s not found", id))));
        Mono<List<OrderItemEntity>> orderItemsMono = orderItemRepository.findByOrderId(id).collectList();
        return Mono.zip(orderMono, orderItemsMono, orderMapper::toOrderDto);
    }
}
