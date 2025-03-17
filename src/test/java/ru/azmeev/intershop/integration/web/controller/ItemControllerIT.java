package ru.azmeev.intershop.integration.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.azmeev.intershop.integration.IntegrationTestBase;
import ru.azmeev.intershop.web.controller.ItemController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerIT extends IntegrationTestBase {

    @Autowired
    private ItemController itemController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setViewResolvers(new InternalResourceViewResolver("/templates/", ".html"))
                .build();
    }

    @Test
    void getHome_shouldReturnRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main/items"));
    }

    @Test
    void getItems_shouldReturnHtmlWithItems() throws Exception {
        mockMvc.perform(get("/main/items"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("search"))
                .andExpect(model().attributeExists("sort"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("paging"))
                .andExpect(view().name("main"));
    }

    @Test
    void getItem_shouldReturnHtmlWithItem() throws Exception {
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(view().name("item"));
    }

    @Test
    void updateCartItemCount_shouldReturnRedirect() throws Exception {
        mockMvc.perform(post("/items/1")
                        .param("action", "PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/1"));
    }

    @Test
    void updateCartItemCountFromMain_shouldReturnRedirect() throws Exception {
        mockMvc.perform(post("/main/items/1")
                        .param("action", "PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main/items"));
    }
}