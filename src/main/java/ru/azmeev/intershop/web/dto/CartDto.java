package ru.azmeev.intershop.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.intershop.model.entity.CartItemEntity;

import java.util.List;

@Getter
@Setter
@Builder
public class CartDto {
    private List<CartItemEntity> cartItems;
    private double total;
    private boolean isEmpty;
}
