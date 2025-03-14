package ru.azmeev.intershop.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "shop_Order")
@Table(name = "SHOP_ORDER")
public class OrderEntity extends BaseEntity {
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItemEntity> orderItems;
}
