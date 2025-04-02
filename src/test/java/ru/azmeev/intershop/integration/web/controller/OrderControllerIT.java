package ru.azmeev.intershop.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.azmeev.intershop.integration.IntegrationTestBase;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.service.CartService;
import ru.azmeev.intershop.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderControllerIT extends IntegrationTestBase {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void getOrders_shouldReturnHtmlWithOrders() {
        webTestClient.get()
                        .uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                });
    }

    @Test
    void getOrder_shouldReturnHtmlWithOrder() {
        cartService.updateCartItemCount(1L, ActionType.PLUS).block();
        OrderEntity order = orderService.createOrder().block();

        webTestClient.get()
                .uri("/orders/" + order.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                });
    }

    @Test
    void createOrder_shouldReturnRedirect() {
        cartService.updateCartItemCount(1L, ActionType.PLUS).block();

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/1?newOrder=true");
    }
}