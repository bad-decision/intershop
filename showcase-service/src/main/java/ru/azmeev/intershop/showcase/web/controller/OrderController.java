package ru.azmeev.intershop.showcase.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.service.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public Mono<String> getOrders(Model model,
                                  @AuthenticationPrincipal UserEntity currentUser) {
        return orderService.getOrders(currentUser.getId())
                .doOnNext(orders -> model.addAttribute("orders", orders))
                .then(Mono.just("orders"));
    }

    @GetMapping("/orders/{id}")
    public Mono<String> getOrder(@PathVariable Long id,
                                 @RequestParam(defaultValue = "false") boolean newOrder,
                                 @AuthenticationPrincipal UserEntity currentUser,
                                 Model model) {
        return orderService.getOrder(id, currentUser.getId())
                .doOnNext(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("newOrder", newOrder);
                })
                .then(Mono.just("order"));
    }

    @PostMapping("/buy")
    public Mono<String> createOrder(Model model,
                                    @AuthenticationPrincipal UserEntity currentUser) {
        return orderService.createOrder(currentUser)
                .flatMap(order -> Mono.just(String.format("redirect:/orders/%s?newOrder=true", order.getId())))
                .onErrorResume(exc -> {
                    model.addAttribute("message", exc.getMessage());
                    return Mono.just("orderError");
                });
    }
}
