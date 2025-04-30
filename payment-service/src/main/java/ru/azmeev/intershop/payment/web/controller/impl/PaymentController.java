package ru.azmeev.intershop.payment.web.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.service.PaymentService;
import ru.azmeev.intershop.payment.web.controller.PaymentApi;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

import java.security.Principal;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "PaymentController", description = "All payment operations")
@RequiredArgsConstructor
@Validated
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @GetMapping("/balance")
    public Mono<BalanceResponse> getBalance(@AuthenticationPrincipal Principal principal) {
        return paymentService.getBalance(principal.getName());
    }

    @PostMapping("/process")
    public Mono<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request,
                                                @AuthenticationPrincipal Principal principal) {
        return paymentService.processPayment(request, principal.getName());
    }
}
