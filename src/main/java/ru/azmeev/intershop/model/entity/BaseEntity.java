package ru.azmeev.intershop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    protected Instant lastModifiedDate;
}
