package ru.azmeev.intershop.showcase.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.service.CartService;
import ru.azmeev.intershop.showcase.service.OrderService;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;

import java.util.ArrayList;

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
    private UserEntity user;
    private OAuth2AuthenticationToken authentication;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);

        user = new UserEntity();
        user.setUsername("user01");
        user.setId(1L);
        authentication = new OAuth2AuthenticationToken(user, new ArrayList<>(), "keycloak");
    }

    @Test
    void getOrders_shouldReturnHtmlWithOrders() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .get()
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
    void getOrders_shouldReturnRedirectKeycloak() {
        webTestClient.get()
                .uri("/orders")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }

    @Test
    void getOrder_shouldReturnHtmlWithOrder() {
        Mockito.when(paymentService.process(10.0, user)).thenReturn(Mono.just(new PaymentResponse().success(true)));
        cartService.updateCartItemCount(1L, ActionType.PLUS, user.getId()).block();
        OrderEntity order = orderService.createOrder(user).block();

        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .get()
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
    void getOrder_shouldReturnRedirectKeycloak() {
        webTestClient.get()
                .uri("/orders/" + 1L)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }

    @Test
    void createOrder_shouldReturnRedirect() {
        Mockito.when(paymentService.process(10.0, user)).thenReturn(Mono.just(new PaymentResponse().success(true)));
        cartService.updateCartItemCount(1L, ActionType.PLUS, user.getId()).block();

        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/3?newOrder=true");
    }

    @Test
    void createOrder_shouldReturnRedirectKeycloak() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }
}