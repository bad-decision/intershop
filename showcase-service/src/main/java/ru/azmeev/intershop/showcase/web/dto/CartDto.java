package ru.azmeev.intershop.showcase.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto implements Serializable {
    private List<ItemDto> cartItems;
    private double total;
    private boolean isEmpty;
    private boolean isPaymentEnable;
}
