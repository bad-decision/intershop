package ru.azmeev.intershop.showcase.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "SHOP_CART_ITEM")
public class CartItemEntity extends BaseEntity {
    @Column("ITEM_ID")
    @NotNull
    private Long itemId;

    @Column("COUNT")
    @NotNull
    private Long count;

    @Column("USER_ID")
    @NotNull
    private Long userId;
}
