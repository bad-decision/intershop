package ru.azmeev.intershop.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.intershop.model.enums.SortType;

@Getter
@Setter
@Builder
public class ItemFilterDto {
    private String search;
    private SortType sort;
    private Integer pageSize;
    private Integer pageNumber;
}
