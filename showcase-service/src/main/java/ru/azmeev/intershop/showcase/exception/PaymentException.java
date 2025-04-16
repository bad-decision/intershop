package ru.azmeev.intershop.showcase.exception;

import lombok.Getter;
import ru.azmeev.intershop.showcase.web.dto.payment.ErrorResponse;

@Getter
public class PaymentException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public PaymentException(ErrorResponse errorResponse) {
        super(errorResponse.getDetails());
        this.errorResponse = errorResponse;
    }
}
