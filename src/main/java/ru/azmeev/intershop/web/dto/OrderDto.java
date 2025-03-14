package ru.azmeev.intershop.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private Double totalSum;
}
