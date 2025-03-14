package ru.azmeev.intershop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.web.dto.CartDto;
import ru.azmeev.intershop.service.CartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        CartDto cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/items/{id}")
    public String updateCartItemCount(@RequestParam ActionType action,
                                      @PathVariable Long id) {
        cartService.updateCartItemCount(id, action);
        return "redirect:/cart";
    }
}
