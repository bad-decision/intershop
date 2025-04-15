package ru.azmeev.intershop.showcase.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.azmeev.intershop.showcase.model.enums.SortType;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class ItemFilterDto implements Serializable {
    private String search;
    private SortType sort;
    private Integer pageSize;
    private Integer pageNumber;
}
