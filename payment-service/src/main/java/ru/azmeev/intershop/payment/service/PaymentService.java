package ru.azmeev.intershop.payment.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

public interface PaymentService {

    Mono<BalanceResponse> getBalance(String username);

    Mono<PaymentResponse> processPayment(PaymentRequest request, String username);
}
