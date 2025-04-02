package ru.azmeev.intershop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.service.CartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Mono<String> getCart(Model model) {
        return cartService.getCart()
                .doOnNext(cart -> model.addAttribute("cart", cart))
                .then(Mono.just("cart"));
    }

    @PostMapping("/items/{id}")
    public Mono<String> updateCartItemCount(ServerWebExchange exchange,
                                            @PathVariable Long id) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String action = formData.getFirst("action");
                    return cartService.updateCartItemCount(id, ActionType.valueOf(action))
                            .then(Mono.just("redirect:/cart"));
                });
    }
}
