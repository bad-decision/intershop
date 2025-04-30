package ru.azmeev.intershop.payment.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.azmeev.intershop.payment.integration.IntegrationTestBase;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

import java.math.BigDecimal;

public class PaymentControllerTest extends IntegrationTestBase {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(username = "user01")
    void getBalance_ReturnsBalanceResponse() {
        BalanceResponse expectedResponse = new BalanceResponse()
                .balance(BigDecimal.valueOf(1000))
                .username("user01");

        webTestClient.get()
                .uri("/api/payment/balance")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BalanceResponse.class)
                .isEqualTo(expectedResponse);
    }

    @Test
    void getBalance_Returns401Error() {
        webTestClient.get()
                .uri("/api/payment/balance")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(username = "user01")
    void processPayment_ReturnsPaymentResponse() {
        PaymentRequest request = new PaymentRequest().amount(new BigDecimal(100)).userId(1L);
        PaymentResponse response = new PaymentResponse().message("Payment processed successfully").success(true).remainingBalance(BigDecimal.valueOf(900));

        webTestClient.post()
                .uri("/api/payment/process")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaymentResponse.class)
                .isEqualTo(response);
    }

    @Test
    void processPayment_Returns401Error() {
        PaymentRequest request = new PaymentRequest().amount(new BigDecimal(100)).userId(1L);

        webTestClient.post()
                .uri("/api/payment/process")
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();;
    }
}
