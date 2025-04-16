package ru.azmeev.intershop.payment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;
import ru.azmeev.intershop.payment.exception.InsufficientFundsException;
import ru.azmeev.intershop.payment.repository.AccountRepository;
import ru.azmeev.intershop.payment.service.impl.PaymentServiceImpl;
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

    @Test
    void getBalance_returnSuccessBalance() {
        BigDecimal expectedBalance = new BigDecimal("1000.0");
        when(accountRepository.getBalance()).thenReturn(expectedBalance);
        StepVerifier.create(paymentService.getBalance())
                .assertNext(balance -> {
                    assertNotNull(balance);
                    assertEquals(expectedBalance, balance.getBalance());
                })
                .verifyComplete();
        verify(accountRepository, times(1)).getBalance();
    }

    @Test
    void processPayment_returnSuccessResponse() {
        BigDecimal balance = new BigDecimal("1000.0");
        BigDecimal cartTotal = new BigDecimal("500.0");
        when(accountRepository.getBalance()).thenReturn(balance);
        PaymentRequest request = new PaymentRequest().amount(cartTotal);
        StepVerifier.create(paymentService.processPayment(request))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(balance.subtract(cartTotal), response.getRemainingBalance());
                    assertEquals(true, response.getSuccess());
                })
                .verifyComplete();
        verify(accountRepository, times(1)).getBalance();
    }

    @Test
    void processPayment_returnException() {
        BigDecimal balance = new BigDecimal("500.0");
        BigDecimal cartTotal = new BigDecimal("1000.0");
        when(accountRepository.getBalance()).thenReturn(balance);
        PaymentRequest request = new PaymentRequest().amount(cartTotal);
        StepVerifier.create(paymentService.processPayment(request))
                .verifyError(InsufficientFundsException.class);
    }
}