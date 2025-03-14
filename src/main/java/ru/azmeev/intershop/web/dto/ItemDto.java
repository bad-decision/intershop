package ru.azmeev.intershop.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemDto {
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Double price;
    private Long count;
}
