package ru.azmeev.intershop.showcase.web.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemAddDto {
    private String title;
    private String description;
    private String imgPath;
    private Double price;
}
