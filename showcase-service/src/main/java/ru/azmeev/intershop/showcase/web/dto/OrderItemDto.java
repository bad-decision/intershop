package ru.azmeev.intershop.showcase.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemDto {
    private Long id;
    private Long itemId;
    private String title;
    private String description;
    private String imgPath;
    private Long count;
    private Double price;
}
