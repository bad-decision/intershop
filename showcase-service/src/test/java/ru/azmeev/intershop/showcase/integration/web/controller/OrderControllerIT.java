package ru.azmeev.intershop.showcase.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.service.CartService;
import ru.azmeev.intershop.showcase.service.OrderService;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderControllerIT extends IntegrationTestBase {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private PaymentService paymentService;

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
        Mockito.when(paymentService.process(10.0)).thenReturn(Mono.just(new PaymentResponse().success(true)));
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
        Mockito.when(paymentService.process(10.0)).thenReturn(Mono.just(new PaymentResponse().success(true)));
        cartService.updateCartItemCount(1L, ActionType.PLUS).block();

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/1?newOrder=true");
    }
}