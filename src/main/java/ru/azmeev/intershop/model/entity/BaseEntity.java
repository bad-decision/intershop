package ru.azmeev.intershop.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @Column("ID")
    private Long id;
}
