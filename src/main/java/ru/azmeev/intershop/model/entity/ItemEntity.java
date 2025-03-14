package ru.azmeev.intershop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_Item")
@Table(name = "SHOP_ITEM")
public class ItemEntity extends BaseEntity {
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMG_PATH")
    private String imgPath;

    @Column(name = "PRICE", nullable = false)
    private Double price;
}
