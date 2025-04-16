package ru.azmeev.intershop.showcase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.service.CacheItemService;
import ru.azmeev.intershop.showcase.service.CartService;
import ru.azmeev.intershop.showcase.service.PaymentService;
import ru.azmeev.intershop.showcase.web.dto.CartDto;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;
import ru.azmeev.intershop.showcase.web.mapper.ItemMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CacheItemService cacheItemService;
    private final PaymentService paymentService;
    private final ItemMapper itemMapper;

    @Override
    public Mono<CartDto> getCart() {
        return cartItemRepository.getCartItems()
                .collectList()
                .flatMap(cartItems -> {
                    List<Long> itemIds = cartItems.stream().map(CartItemEntity::getItemId).toList();
                    if (itemIds.size() == 0) {
                        return Mono.just(CartDto.builder()
                                .cartItems(new ArrayList<>())
                                .isEmpty(cartItems.isEmpty())
                                .total(0)
                                .build());
                    }

                    return cacheItemService.findByIds(itemIds)
                            .flatMap(items -> {
                                List<ItemDto> itemDtos = itemMapper.toItemDto(items, cartItems);
                                double cartTotal = itemDtos.stream()
                                        .map(item -> item.getCount() * item.getPrice())
                                        .reduce(0.0, Double::sum);
                                CartDto cartDto = CartDto.builder()
                                        .cartItems(itemDtos)
                                        .isEmpty(cartItems.isEmpty())
                                        .total(cartTotal)
                                        .build();

                                return paymentService.getBalance()
                                        .map(balanceResponse -> {
                                            boolean isPaymentEnable = balanceResponse.getBalance() != null &&
                                                    balanceResponse.getBalance().compareTo(BigDecimal.valueOf(cartTotal)) >= 0;
                                            cartDto.setPaymentEnable(isPaymentEnable);
                                            return cartDto;
                                        })
                                        .onErrorReturn(cartDto);
                            });
                });
    }

    @Override
    @Transactional
    public Mono<Void> updateCartItemCount(Long itemId, ActionType action) {
        Mono<ItemEntity> itemMono = cacheItemService.findById(itemId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("Item with id %s not found", itemId))));
        Mono<Optional<CartItemEntity>> cartItemMono = cartItemRepository.findByItem(itemId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());
        return Mono.zip(itemMono, cartItemMono)
                .flatMap(tuple -> {
                    CartItemEntity cartItem = tuple.getT2().orElse(null);

                    switch (action) {
                        case PLUS -> {
                            if (cartItem == null) {
                                cartItem = new CartItemEntity();
                                cartItem.setItemId(itemId);
                                cartItem.setCount(0L);
                            }
                            cartItem.setCount(cartItem.getCount() + 1);
                            return cartItemRepository.save(cartItem).then();
                        }
                        case MINUS -> {
                            if (cartItem != null) {
                                if (cartItem.getCount() > 1) {
                                    cartItem.setCount(cartItem.getCount() - 1);
                                    return cartItemRepository.save(cartItem).then();
                                } else {
                                    return cartItemRepository.delete(cartItem);
                                }
                            }
                        }
                        case DELETE -> {
                            if (cartItem != null) {
                                return cartItemRepository.delete(cartItem);
                            }
                        }
                    }
                    return Mono.empty();
                });
    }
}