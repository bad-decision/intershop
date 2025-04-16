package ru.azmeev.intershop.showcase.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
