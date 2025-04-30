package ru.azmeev.intershop.showcase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.azmeev.intershop.showcase.model.entity.*;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.repository.OrderItemRepository;
import ru.azmeev.intershop.showcase.repository.OrderRepository;
import ru.azmeev.intershop.showcase.service.impl.OrderServiceImpl;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;
import ru.azmeev.intershop.showcase.web.mapper.OrderItemMapper;
import ru.azmeev.intershop.showcase.web.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private PaymentService paymentService;
    @Spy
    private OrderItemMapper orderItemMapper = new OrderItemMapper();
    @Spy
    private OrderMapper orderMapper = new OrderMapper(orderItemMapper);
    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Long ORDER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long CART_ITEM_ID = 1L;
    private CartItemEntity testCartItem;
    private OrderEntity testOrder;
    private OrderItemEntity testOrderItem;
    private ItemEntity testItem;
    private UserEntity user;

    @BeforeEach
    void prepare() {
        testItem = new ItemEntity();
        testItem.setId(ITEM_ID);
        testItem.setPrice(100.0);
        testItem.setTitle("ITEM");

        testCartItem = new CartItemEntity();
        testCartItem.setId(CART_ITEM_ID);
        testCartItem.setCount(2L);
        testCartItem.setItemId(ITEM_ID);

        testOrderItem = new OrderItemEntity();
        testOrderItem.setTitle(testItem.getTitle());
        testOrderItem.setPrice(testItem.getPrice());
        testOrderItem.setCount(2L);
        testOrderItem.setOrderId(ORDER_ID);
        testOrderItem.setItemId(ITEM_ID);

        testOrder = new OrderEntity();
        testOrder.setId(ORDER_ID);

        user = new UserEntity();
        user.setUsername("user01");
        user.setId(USER_ID);
    }

    @Test
    void createOrder_success() {
        when(cartItemRepository.getCartItems(USER_ID)).thenReturn(Flux.just(testCartItem));
        when(itemRepository.findByIds(List.of(ITEM_ID))).thenReturn(Flux.just(testItem));
        when(orderRepository.save(any())).thenReturn(Mono.just(testOrder));
        when(orderItemRepository.saveAll((Iterable<OrderItemEntity>) any())).thenReturn(Flux.empty());
        when(cartItemRepository.deleteAll(List.of(testCartItem))).thenReturn(Flux.empty().then());
        when(paymentService.process(200.0, user)).thenReturn(Mono.just(new PaymentResponse().success(true).remainingBalance(BigDecimal.valueOf(100.0))));

        StepVerifier.create(orderService.createOrder(user))
                .assertNext(order -> {
                    assertEquals(testOrder.getId(), order.getId());
                })
                .verifyComplete();
        verify(cartItemRepository, times(1)).deleteAll(List.of(testCartItem));
        verify(orderRepository, times(1)).save(any());
        verify(orderItemRepository, times(1)).saveAll((Iterable<OrderItemEntity>) any());
    }

    @Test
    void createOrder_paymentError() {
        when(cartItemRepository.getCartItems(USER_ID)).thenReturn(Flux.just(testCartItem));
        when(itemRepository.findByIds(List.of(ITEM_ID))).thenReturn(Flux.just(testItem));
        when(paymentService.process(200.0, user)).thenReturn(Mono.just(new PaymentResponse().success(false).message("Balance is low")));

        StepVerifier.create(orderService.createOrder(user))
                .verifyError(IllegalArgumentException.class);
    }

    @Test
    void getOrders_success() {
        when(orderRepository.findByUserId(USER_ID)).thenReturn(Flux.just(testOrder));
        when(orderItemRepository.findByOrderIds(List.of(testOrder.getId()))).thenReturn(Flux.just(testOrderItem));

        StepVerifier.create(orderService.getOrders(USER_ID))
                .assertNext(orders -> {
                    assertEquals(1, orders.size());
                    assertEquals(testOrder.getId(), orders.get(0).getId());
                })
                .verifyComplete();
        verify(orderRepository, times(1)).findByUserId(USER_ID);
        verify(orderItemRepository, times(1)).findByOrderIds(List.of(testOrder.getId()));
    }

    @Test
    void getOrder_success() {
        when(orderRepository.findByIdAndUserId(ORDER_ID, USER_ID)).thenReturn(Mono.just(testOrder));
        when(orderItemRepository.findByOrderId(ORDER_ID)).thenReturn(Flux.just(testOrderItem));

        StepVerifier.create(orderService.getOrder(ORDER_ID, USER_ID))
                .assertNext(order -> {
                    assertEquals(testOrder.getId(), order.getId());
                })
                .verifyComplete();
        verify(orderRepository, times(1)).findByIdAndUserId(ORDER_ID, USER_ID);
    }

    @Test
    void getOrder_throwException() {
        when(orderRepository.findByIdAndUserId(ORDER_ID, USER_ID)).thenReturn(Mono.empty());
        when(orderItemRepository.findByOrderId(ORDER_ID)).thenReturn(Flux.empty());

        StepVerifier.create(orderService.getOrder(ORDER_ID, USER_ID))
                .verifyError(IllegalArgumentException.class);
    }
}