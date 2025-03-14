package ru.azmeev.intershop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.web.dto.CartDto;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
import ru.azmeev.intershop.service.CartService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    @Override
    public CartDto getCart() {
        List<CartItemEntity> cartItems = cartItemRepository.getCartItems();
        Double total = cartItems.stream()
                .map(cartItem -> cartItem.getCount() * cartItem.getItem().getPrice())
                .reduce(0.0, Double::sum);
        return CartDto.builder()
                .cartItems(cartItems)
                .isEmpty(cartItems.isEmpty())
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public void updateCartItemCount(Long itemId, ActionType action) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Item with id %s not found", itemId)));
        CartItemEntity cartItem = cartItemRepository.findByItem(itemId).orElse(null);
        switch (action) {
            case PLUS -> {
                if (cartItem == null) {
                    cartItem = new CartItemEntity();
                    cartItem.setItem(item);
                    cartItem.setCount(0L);
                }
                cartItem.setCount(cartItem.getCount() + 1);
                cartItemRepository.save(cartItem);
            }
            case MINUS -> {
                if (cartItem != null) {
                    if (cartItem.getCount() > 1) {
                        cartItem.setCount(cartItem.getCount() - 1);
                        cartItemRepository.save(cartItem);
                    } else {
                        cartItemRepository.delete(cartItem);
                    }
                }
            }
            case DELETE -> {
                if (cartItem != null) {
                    cartItemRepository.delete(cartItem);
                }
            }
        }
    }
}
