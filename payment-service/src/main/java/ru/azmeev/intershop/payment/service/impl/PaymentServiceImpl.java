package ru.azmeev.intershop.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.payment.exception.InsufficientFundsException;
import ru.azmeev.intershop.payment.repository.AccountRepository;
import ru.azmeev.intershop.payment.service.PaymentService;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;
import ru.azmeev.intershop.payment.web.dto.PaymentResponse;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AccountRepository accountRepository;

    @Override
    public Mono<BalanceResponse> getBalance(String username) {
        return accountRepository.getAccount(username)
                .map(account -> new BalanceResponse()
                        .username(account.getUsername())
                        .balance(account.getBalance()));
    }

    @Override
    public Mono<PaymentResponse> processPayment(PaymentRequest request, String username) {
        return accountRepository.getAccount(username).flatMap(account -> {
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                return Mono.error(new InsufficientFundsException(account.getBalance(), request.getAmount()));
            } else {
                account.setBalance(account.getBalance().subtract(request.getAmount()));
                return accountRepository.save(account)
                        .flatMap(savedAccount -> Mono.just(new PaymentResponse()
                                .success(true)
                                .remainingBalance(savedAccount.getBalance())
                                .message("Payment processed successfully")));
            }
        });
    }
}
