package ru.azmeev.intershop.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.azmeev.intershop.integration.IntegrationTestBase;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartItemRepositoryIT extends IntegrationTestBase {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    private static final Long ITEM_ID = 1L;
    private static final Long ITEM_COUNT = 2L;
    private ItemEntity expectedItem;

    @BeforeEach
    void initCartItems() {
        expectedItem = itemRepository.findById(ITEM_ID).orElse(null);
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCount(ITEM_COUNT);
        cartItem.setItem(expectedItem);
        cartItemRepository.save(cartItem);
    }

    @Test
    void getCartItems_mustReturnList() {
        List<CartItemEntity> cartItems = cartItemRepository.getCartItems();
        ItemEntity item = cartItems.get(0).getItem();
        assertEquals(expectedItem.getId(), item.getId());
        assertEquals(expectedItem.getTitle(), item.getTitle());
        assertEquals(ITEM_COUNT, cartItems.get(0).getCount());
        assertEquals(1, cartItems.size());
    }

    @Test
    void findByItem_mustReturnItem() {
        Optional<CartItemEntity> cartItem = cartItemRepository.findByItem(ITEM_ID);
        assertTrue(cartItem.isPresent());
        ItemEntity item = cartItem.get().getItem();
        assertEquals(expectedItem.getId(), item.getId());
        assertEquals(expectedItem.getTitle(), item.getTitle());
    }

    @Test
    void findByItems_mustReturnList() {
        List<CartItemEntity> cartItems = cartItemRepository.findByItems(List.of(ITEM_ID));
        ItemEntity item = cartItems.get(0).getItem();
        assertEquals(expectedItem.getId(), item.getId());
        assertEquals(expectedItem.getTitle(), item.getTitle());
        assertEquals(ITEM_COUNT, cartItems.get(0).getCount());
        assertEquals(1, cartItems.size());
    }
}