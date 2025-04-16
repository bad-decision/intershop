package ru.azmeev.intershop.payment.repository.impl;

import org.springframework.stereotype.Service;
import ru.azmeev.intershop.payment.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountRepositoryImpl implements AccountRepository {
    @Override
    public BigDecimal getBalance() {
        return BigDecimal.valueOf(Math.random() * 1000);
    }
}
