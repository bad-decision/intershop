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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        executeSqlBlocking(initDataSql);

        expectedItem = itemRepository.findById(ITEM_ID).block();
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCount(ITEM_COUNT);
        cartItem.setItemId(ITEM_ID);
        cartItemRepository.save(cartItem).block();
    }

    @Test
    void getCartItems_mustReturnList() {
        List<CartItemEntity> cartItems = cartItemRepository.getCartItems().collectList().block();
        Long itemId = cartItems.get(0).getItemId();
        assertEquals(expectedItem.getId(), itemId);
        assertEquals(ITEM_COUNT, cartItems.get(0).getCount());
        assertEquals(1, cartItems.size());
    }

    @Test
    void findByItem_mustReturnItem() {
        CartItemEntity cartItem = cartItemRepository.findByItem(ITEM_ID).block();
        Long itemId = cartItem.getItemId();
        assertEquals(expectedItem.getId(), itemId);
    }

    @Test
    void findByItems_mustReturnList() {
        List<CartItemEntity> cartItems = cartItemRepository.findByItems(List.of(ITEM_ID)).collectList().block();
        Long itemId = cartItems.get(0).getItemId();
        assertEquals(expectedItem.getId(), itemId);
        assertEquals(ITEM_COUNT, cartItems.get(0).getCount());
        assertEquals(1, cartItems.size());
    }
}