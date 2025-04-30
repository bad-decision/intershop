package ru.azmeev.intershop.payment.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    @Id
    @Column("ID")
    private Long id;
}
