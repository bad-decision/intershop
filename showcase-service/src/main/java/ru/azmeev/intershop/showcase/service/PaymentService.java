package ru.azmeev.intershop.showcase.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.web.dto.payment.BalanceResponse;
import ru.azmeev.intershop.showcase.web.dto.payment.PaymentResponse;

public interface PaymentService {

    Mono<BalanceResponse> getBalance();

    Mono<PaymentResponse> process(double cartTotal);
}
