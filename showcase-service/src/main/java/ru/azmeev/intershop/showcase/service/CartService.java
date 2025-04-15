package ru.azmeev.intershop.showcase.service;

import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.web.dto.CartDto;

public interface CartService {

    Mono<CartDto> getCart();

    Mono<Void> updateCartItemCount(Long itemId, ActionType action);
}
