package ru.azmeev.intershop.payment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.azmeev.intershop.payment.exception.InsufficientFundsException;
import ru.azmeev.intershop.payment.model.AccountEntity;
import ru.azmeev.intershop.payment.repository.AccountRepository;
import ru.azmeev.intershop.payment.service.impl.PaymentServiceImpl;
import ru.azmeev.intershop.payment.web.dto.BalanceResponse;
import ru.azmeev.intershop.payment.web.dto.PaymentRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private PaymentServiceImpl paymentService;
    private final String username = "user01";
    private AccountEntity account;

    @BeforeEach
    public void setUp() {
        account = new AccountEntity();
        account.setBalance(new BigDecimal("1000.0"));
        account.setUsername(username);
    }

    @Test
    void getBalance_returnSuccessBalance() {
        BalanceResponse expectedResponse = new BalanceResponse()
                .balance(account.getBalance())
                .username(username);
        when(accountRepository.getAccount(username)).thenReturn(Mono.just(account));
        StepVerifier.create(paymentService.getBalance(username))
                .assertNext(balance -> {
                    assertNotNull(balance);
                    assertEquals(expectedResponse.getBalance(), balance.getBalance());
                    assertEquals(expectedResponse.getUsername(), balance.getUsername());
                })
                .verifyComplete();
        verify(accountRepository, times(1)).getAccount(username);
    }

    @Test
    void processPayment_returnSuccessResponse() {
        BigDecimal cartTotal = new BigDecimal("500.0");
        AccountEntity savedAccount = new AccountEntity();
        savedAccount.setUsername(username);
        savedAccount.setBalance(account.getBalance().subtract(cartTotal));
        when(accountRepository.getAccount(username)).thenReturn(Mono.just(account));
        when(accountRepository.save(any())).thenReturn(Mono.just(savedAccount));
        PaymentRequest request = new PaymentRequest().amount(cartTotal);
        StepVerifier.create(paymentService.processPayment(request, username))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(savedAccount.getBalance(), response.getRemainingBalance());
                    assertEquals(true, response.getSuccess());
                })
                .verifyComplete();
        verify(accountRepository, times(1)).getAccount(username);
    }

    @Test
    void processPayment_returnException() {
        BigDecimal cartTotal = new BigDecimal("2000.0");
        when(accountRepository.getAccount(username)).thenReturn(Mono.just(account));
        PaymentRequest request = new PaymentRequest().amount(cartTotal);
        StepVerifier.create(paymentService.processPayment(request, username))
                .verifyError(InsufficientFundsException.class);
    }
}