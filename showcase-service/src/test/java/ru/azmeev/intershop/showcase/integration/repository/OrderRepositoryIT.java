package ru.azmeev.intershop.showcase.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;
import ru.azmeev.intershop.showcase.repository.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderRepositoryIT extends IntegrationTestBase {

    @Autowired
    private OrderRepository orderRepository;
    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 1L;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void findByUserId_mustReturnCorrectList() {
        List<OrderEntity> orders = orderRepository.findByUserId(USER_ID).collectList().block();
        assertEquals(2L, orders.size());
    }

    @Test
    void findByIdAndUserId_mustReturnCorrectValue() {
        OrderEntity order = orderRepository.findByIdAndUserId(ORDER_ID, USER_ID).block();
        assertEquals(USER_ID, order.getUserId());
        assertEquals(ORDER_ID, order.getId());
    }

    @Test
    void findByIdAndUserId_mustReturnNull() {
        OrderEntity order = orderRepository.findByIdAndUserId(ORDER_ID, 1000L).block();
        assertNull(order);
    }
}