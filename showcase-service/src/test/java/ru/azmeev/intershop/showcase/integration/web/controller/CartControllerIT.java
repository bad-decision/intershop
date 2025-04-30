package ru.azmeev.intershop.showcase.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartControllerIT extends IntegrationTestBase {

    @Autowired
    private WebTestClient webTestClient;
    private OAuth2AuthenticationToken authentication;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);

        UserEntity principal = new UserEntity();
        principal.setUsername("user01");
        principal.setId(1L);
        authentication = new OAuth2AuthenticationToken(principal, new ArrayList<>(), "keycloak");
    }

    @Test
    void getCart_shouldReturnHtmlWithCart() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .get()
                .uri("/cart")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                });
    }

    @Test
    void getCart_shouldReturnRedirectKeycloak() {
        webTestClient.get()
                .uri("/cart")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }

    @Test
    void updateCartItemCount_shouldReturnRedirect() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .post()
                .uri("/cart/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/cart");
    }

    @Test
    void updateCartItemCount_shouldReturnRedirectKeycloak() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/cart/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }
}