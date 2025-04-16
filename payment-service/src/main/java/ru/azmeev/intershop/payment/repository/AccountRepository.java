package ru.azmeev.intershop.payment.repository;

import java.math.BigDecimal;

public interface AccountRepository {

    BigDecimal getBalance();
}
