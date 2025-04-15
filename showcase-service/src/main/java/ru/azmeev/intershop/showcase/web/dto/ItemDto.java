package ru.azmeev.intershop.showcase.web.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Double price;
    private Long count;
}
