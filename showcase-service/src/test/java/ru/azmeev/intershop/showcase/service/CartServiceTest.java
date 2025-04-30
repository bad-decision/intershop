package ru.azmeev.intershop.showcase.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.model.enums.ActionType;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.service.impl.CartServiceImpl;
import ru.azmeev.intershop.showcase.web.dto.payment.BalanceResponse;
import ru.azmeev.intershop.showcase.web.mapper.ItemMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CacheItemService cacheItemService;
    @Mock
    private PaymentService paymentService;
    @Spy
    private ItemMapper itemMapper;
    @InjectMocks
    private CartServiceImpl cartService;

    private static final Long CART_ITEM_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long USER_ID = 1L;
    private ItemEntity testItem;
    private CartItemEntity testCartItem;
    private UserEntity user;

    @BeforeEach
    void prepare() {
        testItem = new ItemEntity();
        testItem.setId(ITEM_ID);
        testItem.setPrice(100.0);
        testItem.setTitle("ITEM");

        testCartItem = new CartItemEntity();
        testCartItem.setId(CART_ITEM_ID);
        testCartItem.setCount(2L);
        testCartItem.setItemId(ITEM_ID);

        user = new UserEntity();
        user.setId(USER_ID);
        user.setUsername("user01");
    }

    @AfterAll
    static void resetMocks() {
        Mockito.reset();
    }

    @Test
    void getCart_success() {
        when(cartItemRepository.getCartItems(USER_ID)).thenReturn(Flux.just(testCartItem));
        when(cacheItemService.findByIds(List.of(ITEM_ID))).thenReturn(Mono.just(List.of(testItem)));
        when(paymentService.getBalance(user)).thenReturn(Mono.just(new BalanceResponse().balance(BigDecimal.valueOf(1000.0))));
        StepVerifier.create(cartService.getCart(user))
                .assertNext(cart -> {
                    assertNotNull(cart);
                    assertEquals(1, cart.getCartItems().size());
                    assertEquals(200.0, cart.getTotal());
                })
                .verifyComplete();
        verify(cartItemRepository, times(1)).getCartItems(USER_ID);
    }

    @Test
    void getEmptyCart_success() {
        when(cartItemRepository.getCartItems(USER_ID)).thenReturn(Flux.empty());
        StepVerifier.create(cartService.getCart(user))
                .assertNext(cart -> {
                    assertNotNull(cart);
                    assertEquals(0, cart.getCartItems().size());
                    assertEquals(0, cart.getTotal());
                })
                .verifyComplete();
        verify(cartItemRepository, times(1)).getCartItems(USER_ID);
    }

    @Test
    void plusCartItemCount_success() {
        when(cacheItemService.findById(ITEM_ID)).thenReturn(Mono.just(testItem));
        when(cartItemRepository.findByItemAndUser(ITEM_ID, USER_ID)).thenReturn(Mono.empty());
        when(cartItemRepository.save(any())).thenReturn(Mono.empty());
        StepVerifier.create(cartService.updateCartItemCount(ITEM_ID, ActionType.PLUS, USER_ID))
                .verifyComplete();
        verify(cartItemRepository, times(1)).save(any());
    }

    @Test
    void minusCartItemCount_success() {
        when(cacheItemService.findById(ITEM_ID)).thenReturn(Mono.just(testItem));
        when(cartItemRepository.findByItemAndUser(ITEM_ID, USER_ID)).thenReturn(Mono.just(testCartItem));
        when(cartItemRepository.save(any())).thenReturn(Mono.empty());
        when(cartItemRepository.delete(any())).thenReturn(Mono.empty());

        StepVerifier.create(cartService.updateCartItemCount(ITEM_ID, ActionType.MINUS, USER_ID))
                .verifyComplete();
        verify(cartItemRepository, times(1)).save(any());
        StepVerifier.create(cartService.updateCartItemCount(ITEM_ID, ActionType.MINUS, USER_ID))
                .verifyComplete();
        verify(cartItemRepository, times(1)).delete(any());
    }

    @Test
    void deleteCartItemCount_success() {
        when(cacheItemService.findById(ITEM_ID)).thenReturn(Mono.just(testItem));
        when(cartItemRepository.findByItemAndUser(ITEM_ID, USER_ID)).thenReturn(Mono.just(testCartItem));
        when(cartItemRepository.delete(any())).thenReturn(Mono.empty());

        StepVerifier.create(cartService.updateCartItemCount(ITEM_ID, ActionType.DELETE, USER_ID))
                .verifyComplete();
        verify(cartItemRepository, times(1)).delete(any());
    }

    @Test
    void plusCartItemCount_throwException() {
        when(cacheItemService.findById(ITEM_ID)).thenReturn(Mono.empty());
        when(cartItemRepository.findByItemAndUser(ITEM_ID, USER_ID)).thenReturn(Mono.just(testCartItem));

        StepVerifier.create(cartService.updateCartItemCount(ITEM_ID, ActionType.PLUS, USER_ID))
                .verifyError(IllegalArgumentException.class);
    }
}