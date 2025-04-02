package ru.azmeev.intershop.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SHOP_ORDER_ITEM")
public class OrderItemEntity extends BaseEntity {
    @NotNull
    @Column("ORDER_ID")
    private Long orderId;

    @NotNull
    @Column("ITEM_ID")
    private Long itemId;

    @NotNull
    @Column("TITLE")
    private String title;

    @Column("DESCRIPTION")
    private String description;

    @Column("IMG_PATH")
    private String imgPath;

    @NotNull
    @Column("COUNT")
    private Long count;

    @NotNull
    @Column("PRICE")
    private Double price;
}