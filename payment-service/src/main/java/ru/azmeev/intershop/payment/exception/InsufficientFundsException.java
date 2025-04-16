package ru.azmeev.intershop.payment.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    private final BigDecimal currentBalance;
    private final BigDecimal requiredAmount;

    public InsufficientFundsException(BigDecimal currentBalance, BigDecimal requiredAmount) {
        super("Insufficient funds for payment");
        this.currentBalance = currentBalance;
        this.requiredAmount = requiredAmount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getRequiredAmount() {
        return requiredAmount;
    }
}

