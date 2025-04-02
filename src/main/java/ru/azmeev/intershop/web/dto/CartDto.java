package ru.azmeev.intershop.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CartDto {
    private List<ItemDto> cartItems;
    private double total;
    private boolean isEmpty;
}
