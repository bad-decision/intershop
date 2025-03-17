package ru.azmeev.intershop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.entity.OrderItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.OrderItemRepository;
import ru.azmeev.intershop.repository.OrderRepository;
import ru.azmeev.intershop.service.impl.OrderServiceImpl;
import ru.azmeev.intershop.web.dto.OrderDto;
import ru.azmeev.intershop.web.mapper.OrderItemMapper;
import ru.azmeev.intershop.web.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Spy
    private OrderItemMapper orderItemMapper = new OrderItemMapper();
    @Spy
    private OrderMapper orderMapper = new OrderMapper(orderItemMapper);
    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Long ORDER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long CART_ITEM_ID = 1L;
    private CartItemEntity testCartItem;
    private OrderEntity testOrder;

    @BeforeEach
    void prepare() {
        ItemEntity testItem = new ItemEntity();
        testItem.setId(ITEM_ID);
        testItem.setPrice(100.0);
        testItem.setTitle("ITEM");

        testCartItem = new CartItemEntity();
        testCartItem.setId(CART_ITEM_ID);
        testCartItem.setCount(2L);
        testCartItem.setItem(testItem);

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setTitle(testItem.getTitle());
        orderItem.setPrice(testItem.getPrice());
        orderItem.setCount(2L);

        testOrder = new OrderEntity();
        testOrder.setId(ORDER_ID);
        testOrder.setOrderItems(List.of(orderItem));
    }

    @Test
    void createOrder_success() {
        doReturn(List.of(testCartItem)).when(cartItemRepository).getCartItems();
        doReturn(testOrder).when(orderRepository).save(any());
        doReturn(Optional.of(testOrder)).when(orderRepository).findById(any());

        OrderEntity order = orderService.createOrder();
        assertEquals(testOrder.getId(), order.getId());
        assertEquals(testOrder.getOrderItems().size(), order.getOrderItems().size());
        assertEquals(testOrder.getOrderItems().get(0).getPrice(), order.getOrderItems().get(0).getPrice());
        verify(cartItemRepository, times(1)).deleteAll(any());
        verify(orderRepository, times(1)).save(any());
        verify(orderItemRepository, times(1)).saveAll(any());
    }

    @Test
    void getOrders_success() {
        doReturn(List.of(testOrder)).when(orderRepository).findAll();

        List<OrderDto> orders = orderService.getOrders();
        assertEquals(1, orders.size());
        assertEquals(testOrder.getId(), orders.get(0).getId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrder_success() {
        doReturn(Optional.of(testOrder)).when(orderRepository).findById(ORDER_ID);

        OrderDto order = orderService.getOrder(ORDER_ID);
        assertEquals(testOrder.getId(), order.getId());
        assertEquals(testOrder.getOrderItems().size(), order.getOrderItems().size());
        verify(orderRepository, times(1)).findById(ORDER_ID);
    }

    @Test
    void getOrder_throwException() {
        doReturn(Optional.empty()).when(orderRepository).findById(ORDER_ID);
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrder(ORDER_ID));
    }
}