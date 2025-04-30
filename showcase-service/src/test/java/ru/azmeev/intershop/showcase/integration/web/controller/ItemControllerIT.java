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

class ItemControllerIT extends IntegrationTestBase {

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
    void getHome_shouldReturnRedirect() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/main/items");
    }

    @Test
    void getItems_shouldReturnHtmlWithItems() {
        webTestClient.get()
                .uri("/main/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                });
    }

    @Test
    void getItem_shouldReturnHtmlWithItem() {
        webTestClient.get()
                .uri("/items/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                });
    }

    @Test
    void updateCartItemCount_shouldReturnRedirect() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .post()
                .uri("/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items/1");
    }

    @Test
    void updateCartItemCount_shouldReturnRedirectKeycloak() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }

    @Test
    void updateCartItemCountFromMain_shouldReturnRedirect() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(authentication))
                .post()
                .uri("/main/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/main/items");
    }

    @Test
    void updateCartItemCountFromMain_shouldReturnRedirectKeycloak() {
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/main/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/oauth2/authorization/keycloak");
    }
}