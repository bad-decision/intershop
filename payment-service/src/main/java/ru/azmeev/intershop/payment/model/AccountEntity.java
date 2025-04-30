package ru.azmeev.intershop.payment.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "PAYMENT_ACCOUNT")
public class AccountEntity extends BaseEntity {

    @NotNull
    @Column("USERNAME")
    protected String username;

    @NotNull
    @Column("BALANCE")
    protected BigDecimal balance;
}
