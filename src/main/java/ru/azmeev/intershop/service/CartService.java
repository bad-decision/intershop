package ru.azmeev.intershop.service;

import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.web.dto.CartDto;

public interface CartService {

    CartDto getCart();

    void updateCartItemCount(Long itemId, ActionType action);
}
