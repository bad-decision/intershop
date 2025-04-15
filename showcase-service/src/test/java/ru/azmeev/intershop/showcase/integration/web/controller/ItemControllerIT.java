package ru.azmeev.intershop.showcase.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemControllerIT extends IntegrationTestBase {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void getHome_shouldReturnRedirect() throws Exception {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/main/items");
    }

    @Test
    void getItems_shouldReturnHtmlWithItems() throws Exception {
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
    void getItem_shouldReturnHtmlWithItem() throws Exception {
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
    void updateCartItemCount_shouldReturnRedirect() throws Exception {
        webTestClient.post()
                .uri("/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items/1");
    }

    @Test
    void updateCartItemCountFromMain_shouldReturnRedirect() throws Exception {
        webTestClient.post()
                .uri("/main/items/1")
                .body(BodyInserters.fromFormData("action", "PLUS"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/main/items");
    }
}