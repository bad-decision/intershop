package ru.azmeev.intershop.showcase.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.model.enums.SortType;
import ru.azmeev.intershop.showcase.service.CartService;
import ru.azmeev.intershop.showcase.service.ItemService;
import ru.azmeev.intershop.showcase.web.dto.ItemAddDto;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;
import ru.azmeev.intershop.showcase.web.dto.ItemFilterDto;
import ru.azmeev.intershop.showcase.web.dto.PagingDto;

import java.util.List;
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
    public Mono<String> home() {
        return Mono.just("redirect:/main/items");
    }

    @GetMapping("/main/items")
    public Mono<String> getItems(@RequestParam Optional<String> search,
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
        model.addAttribute("search", itemFilterDto.getSearch());
        model.addAttribute("sort", itemFilterDto.getSort().toString());

        return itemService.searchItems(itemFilterDto)
                .doOnNext(page -> {
                    PagingDto paging = new PagingDto(itemFilterDto.getPageNumber(), page.getSize(), page.hasNext(), page.hasPrevious());
                    model.addAttribute("items", page.getContent());
                    model.addAttribute("paging", paging);

                })
                .then(Mono.just("main"));
    }

    @GetMapping("/items/{id}")
    public Mono<String> getItem(@PathVariable Long id,
                                Model model) {
        return itemService.getItem(id)
                .doOnNext(item -> model.addAttribute("item", item))
                .then(Mono.just("item"));
    }

    @PostMapping("/items/{id}")
    public Mono<String> updateCartItemCount(ServerWebExchange exchange,
                                            @PathVariable Long id,
                                            @AuthenticationPrincipal UserEntity currentUser) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String action = formData.getFirst("action");
                    return cartService.updateCartItemCount(id, ActionType.valueOf(action), currentUser.getId())
                            .then(Mono.just("redirect:/items/{id}"));
                });
    }

    @PostMapping("/main/items/{id}")
    public Mono<String> updateCartItemCountFromMain(ServerWebExchange exchange,
                                                    @PathVariable Long id,
                                                    @AuthenticationPrincipal UserEntity currentUser) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String action = formData.getFirst("action");
                    return cartService.updateCartItemCount(id, ActionType.valueOf(action), currentUser.getId())
                            .then(Mono.just("redirect:/main/items"));
                });
    }

    @PostMapping(value = "/items/add")
    @ResponseBody
    public Flux<ItemDto> addItems(@RequestBody List<ItemAddDto> items) {
        return itemService.addItems(items);
    }
}