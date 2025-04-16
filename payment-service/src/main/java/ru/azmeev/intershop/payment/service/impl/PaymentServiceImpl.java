package ru.azmeev.intershop.payment.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.exception.InsufficientFundsException;
import ru.azmeev.intershop.payment.service.PaymentService;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public Mono<BalanceResponse> getBalance() {
        return Mono.just(new BalanceResponse()
                .balance(BigDecimal.valueOf(Math.random() * 1000)));
    }

    @Override
    public Mono<PaymentResponse> processPayment(PaymentRequest request) {
        return getBalance().flatMap(balanceResponse -> {
            if (balanceResponse.getBalance().compareTo(request.getAmount()) < 0) {
                return Mono.error(new InsufficientFundsException(balanceResponse.getBalance(), request.getAmount()));
            } else {
                return Mono.just(new PaymentResponse()
                        .success(true)
                        .remainingBalance(balanceResponse.getBalance().subtract(request.getAmount()))
                        .message("Payment processed successfully"));
            }
        });
    }
}
