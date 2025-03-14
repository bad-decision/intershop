package ru.azmeev.intershop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.service.OrderService;
import ru.azmeev.intershop.web.dto.OrderDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<OrderDto> orders = orderService.getOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable Long id,
                           @RequestParam(defaultValue = "false") boolean newOrder,
                           Model model) {
        OrderDto order = orderService.getOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("newOrder", newOrder);
        return "order";
    }

    @PostMapping("/buy")
    public String createOrder() {
        OrderEntity order = orderService.createOrder();
        return String.format("redirect:/orders/%s?newOrder=true", order.getId());
    }
}
