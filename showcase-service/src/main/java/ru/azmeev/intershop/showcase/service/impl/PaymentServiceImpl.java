package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.exception.PaymentException;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.payment.BalanceResponse;
import ru.azmeev.intershop.showcase.web.dto.payment.ErrorResponse;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentRequest;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Qualifier("paymentService")
    private final WebClient webClient;

    @Override
    public Mono<BalanceResponse> getBalance() {
        return webClient.get()
                .uri("/api/payment/balance")
                .retrieve()
                .bodyToMono(BalanceResponse.class);
    }

    @Override
    public Mono<PaymentResponse> process(double cartTotal) {
        PaymentRequest paymentRequest = new PaymentRequest().amount(BigDecimal.valueOf(cartTotal));
        return webClient.post()
                .uri("/api/payment/process") // Сохранение пользователя
                .bodyValue(paymentRequest)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .flatMap(errorBody -> Mono.error(new PaymentException(errorBody)))
                )
                .bodyToMono(PaymentResponse.class);
    }
}