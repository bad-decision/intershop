package ru.azmeev.intershop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_CartItem")
@Table(name = "SHOP_CART_ITEM")
public class CartItemEntity extends BaseEntity {
    @JoinColumn(name = "ITEM_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private ItemEntity item;

    @Column(name = "COUNT", nullable = false)
    @NotNull
    private Long count;
}
