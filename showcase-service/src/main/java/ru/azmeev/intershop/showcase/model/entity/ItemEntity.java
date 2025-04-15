package ru.azmeev.intershop.showcase.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "SHOP_ITEM")
public class ItemEntity extends BaseEntity {
    @Column("TITLE")
    private String title;

    @Column("DESCRIPTION")
    private String description;

    @Column("IMG_PATH")
    private String imgPath;

    @Column("PRICE")
    private Double price;
}
