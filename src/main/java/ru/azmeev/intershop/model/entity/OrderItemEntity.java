package ru.azmeev.intershop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shop_OrderItem")
@Table(name = "SHOP_ORDER_ITEM")
public class OrderItemEntity extends BaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private OrderEntity order;

    @NotNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMG_PATH")
    private String imgPath;

    @NotNull
    @Column(name = "COUNT", nullable = false)
    private Long count;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private Double price;
}
