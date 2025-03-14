package ru.azmeev.intershop.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.model.enums.SortType;
import ru.azmeev.intershop.service.CartService;
import ru.azmeev.intershop.service.ItemService;
import ru.azmeev.intershop.web.dto.ItemDto;
import ru.azmeev.intershop.web.dto.PagingDto;

import java.util.Optional;

@Controller
public class ItemController {

    private final Integer defaultPageSize;
    private final Integer defaultPageNumber;
    private final ItemService itemService;
    private final CartService cartService;

    public ItemController(@Value("${shop.defaultPageSize}") Integer defaultPageSize,
                          @Value("${shop.defaultPageNumber}") Integer defaultPageNumber,
                          ItemService itemService,
                          CartService cartService) {
        this.defaultPageSize = defaultPageSize;
        this.defaultPageNumber = defaultPageNumber;
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/main/items";
    }

    @GetMapping("/main/items")
    public String getItems(@RequestParam Optional<String> search,
                           @RequestParam Optional<SortType> sort,
                           @RequestParam Optional<Integer> pageSize,
                           @RequestParam Optional<Integer> pageNumber,
                           Model model) {
        ItemFilterDto itemFilterDto = ItemFilterDto.builder()
                .search(search.orElse(""))
                .sort(sort.orElse(SortType.NO))
                .pageSize(pageSize.orElse(defaultPageSize))
                .pageNumber(pageNumber.orElse(defaultPageNumber))
                .build();
        Page<ItemDto> page = itemService.searchItems(itemFilterDto);
        PagingDto paging = new PagingDto(itemFilterDto.getPageNumber(), page.getSize(), page.hasNext(), page.hasPrevious());
        model.addAttribute("search", itemFilterDto.getSearch());
        model.addAttribute("sort", itemFilterDto.getSort().toString());
        model.addAttribute("items", page.getContent());
        model.addAttribute("paging", paging);
        return "main";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable Long id,
                          Model model) {
        model.addAttribute("item", itemService.getItem(id));
        return "item";
    }

    @PostMapping("/items/{id}")
    public String updateCartItemCount(@RequestParam String action,
                                      @PathVariable Long id) {
        cartService.updateCartItemCount(id, ActionType.valueOf(action));
        return "redirect:/items/{id}";
    }

    @PostMapping("/main/items/{id}")
    public String updateCartItemCountFromMain(@RequestParam String action,
                                              @PathVariable Long id) {
        cartService.updateCartItemCount(id, ActionType.valueOf(action));
        return "redirect:/main/items";
    }
}