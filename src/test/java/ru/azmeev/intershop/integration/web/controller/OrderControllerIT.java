package ru.azmeev.intershop.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.azmeev.intershop.integration.IntegrationTestBase;
import ru.azmeev.intershop.model.entity.OrderEntity;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.service.CartService;
import ru.azmeev.intershop.service.OrderService;
import ru.azmeev.intershop.web.controller.OrderController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerIT extends IntegrationTestBase {

    @Autowired
    private OrderController orderController;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setViewResolvers(new InternalResourceViewResolver("/templates/", ".html"))
                .build();
    }

    @Test
    void getOrders_shouldReturnHtmlWithOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(view().name("orders"));
    }

    @Test
    void getOrder_shouldReturnHtmlWithOrder() throws Exception {
        cartService.updateCartItemCount(1L, ActionType.PLUS);
        OrderEntity order = orderService.createOrder();

        mockMvc.perform(get("/orders/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("newOrder"))
                .andExpect(view().name("order"));
    }

    @Test
    void createOrder_shouldReturnRedirect() throws Exception {
        cartService.updateCartItemCount(1L, ActionType.PLUS);

        mockMvc.perform(post("/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/1?newOrder=true"));
    }
}