package ru.azmeev.intershop.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.web.dto.CartDto;

public interface CartService {

    Mono<CartDto> getCart();

    Mono<Void> updateCartItemCount(Long itemId, ActionType action);
}
