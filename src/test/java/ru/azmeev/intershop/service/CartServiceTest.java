package ru.azmeev.intershop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.model.enums.ActionType;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
import ru.azmeev.intershop.service.impl.CartServiceImpl;
import ru.azmeev.intershop.web.dto.CartDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private CartServiceImpl cartService;
    private static final Long CART_ITEM_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private ItemEntity testItem;
    private CartItemEntity testCartItem;

    @BeforeEach
    void prepare() {
        testItem = new ItemEntity();
        testItem.setId(ITEM_ID);
        testItem.setPrice(100.0);
        testItem.setTitle("ITEM");

        testCartItem = new CartItemEntity();
        testCartItem.setId(CART_ITEM_ID);
        testCartItem.setCount(2L);
        testCartItem.setItem(testItem);
    }

    @Test
    void getCart_success() {
        doReturn(List.of(testCartItem)).when(cartItemRepository).getCartItems();
        CartDto cart = cartService.getCart();
        assertEquals(1, cart.getCartItems().size());
        assertEquals(200.0, cart.getTotal());
        assertFalse(cart.isEmpty());
        verify(cartItemRepository, times(1)).getCartItems();
    }

    @Test
    void plusCartItemCount_success() {
        doReturn(Optional.of(testItem)).when(itemRepository).findById(ITEM_ID);
        doReturn(Optional.empty()).when(cartItemRepository).findByItem(CART_ITEM_ID);

        cartService.updateCartItemCount(ITEM_ID, ActionType.PLUS);
        verify(cartItemRepository, times(1)).save(any());
    }

    @Test
    void minusCartItemCount_success() {
        doReturn(Optional.of(testItem)).when(itemRepository).findById(ITEM_ID);
        doReturn(Optional.of(testCartItem)).when(cartItemRepository).findByItem(CART_ITEM_ID);

        cartService.updateCartItemCount(ITEM_ID, ActionType.MINUS);
        verify(cartItemRepository, times(1)).save(any());
        cartService.updateCartItemCount(ITEM_ID, ActionType.MINUS);
        verify(cartItemRepository, times(1)).delete(any());
    }

    @Test
    void deleteCartItemCount_success() {
        doReturn(Optional.of(testItem)).when(itemRepository).findById(ITEM_ID);
        doReturn(Optional.of(testCartItem)).when(cartItemRepository).findByItem(CART_ITEM_ID);

        cartService.updateCartItemCount(ITEM_ID, ActionType.DELETE);
        verify(cartItemRepository, times(1)).delete(any());
    }

    @Test
    void plusCartItemCount_throwException() {
        doThrow(IllegalArgumentException.class).when(itemRepository).findById(ITEM_ID);
        assertThrows(IllegalArgumentException.class, () -> cartService.updateCartItemCount(ITEM_ID, ActionType.PLUS));
    }
}