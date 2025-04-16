package ru.azmeev.intershop.payment.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.service.PaymentService;
import ru.azmeev.intershop.payment.web.controller.impl.PaymentController;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(controllers = PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private PaymentService paymentService;

    @Test
    void getBalance_ReturnsBalanceResponse() {
        BalanceResponse mockResponse = new BalanceResponse().balance(BigDecimal.valueOf(1000));
        Mockito.when(paymentService.getBalance()).thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri("/api/payment/balance")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BalanceResponse.class)
                .isEqualTo(mockResponse);
    }

    @Test
    void getBalance_ReturnsError() {
        Mockito.when(paymentService.getBalance())
                .thenReturn(Mono.error(new RuntimeException("Balance service unavailable")));

        webTestClient.get()
                .uri("/api/payment/balance")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void processPayment_ReturnsPaymentResponse() {
        PaymentRequest request = new PaymentRequest(BigDecimal.valueOf(100));
        PaymentResponse mockResponse = new PaymentResponse().message("Payment processed").success(true).remainingBalance(BigDecimal.valueOf(100));
        Mockito.when(paymentService.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/api/payment/process")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaymentResponse.class)
                .isEqualTo(mockResponse);
    }

    @Test
    void processPayment_ReturnsValidationError() {
        PaymentRequest invalidRequest = new PaymentRequest(BigDecimal.valueOf(-100));

        webTestClient.post()
                .uri("/api/payment/process")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
