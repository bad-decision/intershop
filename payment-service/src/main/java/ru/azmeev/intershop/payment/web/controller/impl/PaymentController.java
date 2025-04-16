package ru.azmeev.intershop.payment.web.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.service.PaymentService;
import ru.azmeev.intershop.payment.web.controller.PaymentApi;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "PaymentController", description = "All payment operations")
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @GetMapping("/balance")
    public Mono<BalanceResponse> getBalance() {
        return paymentService.getBalance();
    }

    @PostMapping("/process")
    public Mono<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}
