package ru.azmeev.intershop.showcase.web.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ItemAddDto {
    private String title;
    private String description;
    private String imgPath;
    private Double price;
}
