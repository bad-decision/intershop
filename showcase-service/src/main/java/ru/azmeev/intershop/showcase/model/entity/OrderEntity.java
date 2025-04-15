package ru.azmeev.intershop.showcase.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "SHOP_ORDER")
public class OrderEntity extends BaseEntity {
}
