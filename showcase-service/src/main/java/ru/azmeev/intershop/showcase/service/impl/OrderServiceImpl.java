package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.*;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.repository.OrderItemRepository;
import ru.azmeev.intershop.showcase.repository.OrderRepository;
import ru.azmeev.intershop.showcase.service.OrderService;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.OrderDto;
import ru.azmeev.intershop.showcase.web.mapper.OrderItemMapper;
import ru.azmeev.intershop.showcase.web.mapper.OrderMapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private final PaymentService paymentService;

    @Override
    @Transactional
    public Mono<OrderEntity> createOrder(UserEntity user) {
        return cartItemRepository.getCartItems(user.getId())
                .collectList()
                .flatMap(cartItems -> {
                    List<Long> itemIds = cartItems.stream().map(CartItemEntity::getItemId).toList();
                    return itemRepository.findByIds(itemIds)
                            .collectList()
                            .flatMap(items -> {
                                double cartTotal = getCartTotal(cartItems, items);
                                return paymentService.process(cartTotal, user)
                                        .flatMap(paymentResponse -> {
                                            if (Boolean.TRUE.equals(paymentResponse.getSuccess())) {
                                                OrderEntity order = new OrderEntity();
                                                order.setUserId(user.getId());
                                                return orderRepository.save(order)
                                                        .flatMap(savedOrder -> {
                                                            order.setId(savedOrder.getId());
                                                            List<OrderItemEntity> orderItems = orderItemMapper.toOrderItemEntities(items, cartItems, savedOrder.getId());
                                                            return orderItemRepository.saveAll(orderItems).collectList();
                                                        })
                                                        .then(cartItemRepository.deleteAll(cartItems))
                                                        .thenReturn(order);
                                            } else {
                                                return Mono.error(new IllegalArgumentException(paymentResponse.getMessage()));
                                            }
                                        });
                            });
                });
    }

    @Override
    public Mono<List<OrderDto>> getOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .collectList()
                .flatMap(orders -> {
                    List<Long> orderIds = orders.stream().map(BaseEntity::getId).toList();
                    return orderItemRepository.findByOrderIds(orderIds)
                            .collectList()
                            .map(ordersItems -> orders.stream()
                                    .map(order -> {
                                        List<OrderItemEntity> orderItems = ordersItems.stream().filter(x -> x.getOrderId().equals(order.getId())).toList();
                                        return orderMapper.toOrderDto(order, orderItems);
                                    })
                                    .toList());
                });
    }

    @Override
    public Mono<OrderDto> getOrder(Long id, Long userId) {
        Mono<OrderEntity> orderMono = orderRepository.findByIdAndUserId(id, userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("Item with id %s not found", id))));
        Mono<List<OrderItemEntity>> orderItemsMono = orderItemRepository.findByOrderId(id).collectList();
        return Mono.zip(orderMono, orderItemsMono, orderMapper::toOrderDto);
    }

    private double getCartTotal(List<CartItemEntity> cartItems, List<ItemEntity> items) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        cartItems.forEach(cartItem -> {
            items.stream()
                    .filter(it -> it.getId().equals(cartItem.getItemId()))
                    .findFirst()
                    .ifPresent(item -> total.updateAndGet(v -> v + item.getPrice() * cartItem.getCount()));
        });
        return total.get();
    }
}
