package ru.azmeev.intershop.showcase.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.service.CartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Mono<String> getCart(Model model,
                                @AuthenticationPrincipal UserEntity currentUser) {
        return cartService.getCart(currentUser)
                .doOnNext(cart -> model.addAttribute("cart", cart))
                .then(Mono.just("cart"));
    }

    @PostMapping("/items/{id}")
    public Mono<String> updateCartItemCount(ServerWebExchange exchange,
                                            @PathVariable Long id,
                                            @AuthenticationPrincipal UserEntity currentUser) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String action = formData.getFirst("action");
                    return cartService.updateCartItemCount(id, ActionType.valueOf(action), currentUser.getId())
                            .then(Mono.just("redirect:/cart"));
                });
    }
}
