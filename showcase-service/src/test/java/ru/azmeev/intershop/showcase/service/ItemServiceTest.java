package ru.azmeev.intershop.showcase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.azmeev.intershop.showcase.model.entity.CartItemEntity;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.repository.CartItemRepository;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.service.impl.ItemServiceImpl;
import ru.azmeev.intershop.showcase.web.dto.ItemFilterDto;
import ru.azmeev.intershop.showcase.web.mapper.ItemMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Spy
    private ItemMapper itemMapper;
    @InjectMocks
    private ItemServiceImpl itemService;

    private static final Long ITEM_ID = 1L;
    private static final Long CART_ITEM_ID = 1L;
    private CartItemEntity testCartItem;
    private ItemEntity testItem;

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
    }

    @Test
    void searchItems_success() {
        Page<ItemEntity> page = new PageImpl<>(List.of(testItem));
        when(itemRepository.filterItems(any(), any())).thenReturn(Flux.just(testItem));
        when(itemRepository.countFilteredItems(any())).thenReturn(Mono.just(1L));
        when(cartItemRepository.findByItems(any())).thenReturn(Flux.just(testCartItem));
        ItemFilterDto filter = ItemFilterDto.builder()
                .pageNumber(1)
                .pageSize(1)
                .search("text")
                .build();

        StepVerifier.create(itemService.searchItems(filter))
                .assertNext(result -> {
                    assertEquals(1, result.getTotalElements());
                    assertEquals(testCartItem.getCount(), result.getContent().get(0).getCount());
                })
                .verifyComplete();
    }

    @Test
    void getItem_success() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Mono.just(testItem));
        when(cartItemRepository.findByItem(ITEM_ID)).thenReturn(Mono.empty());

        StepVerifier.create(itemService.getItem(ITEM_ID))
                .assertNext(item -> {
                    assertNotNull(item);
                    assertEquals(testItem.getId(), item.getId());
                    assertEquals(0, item.getCount());
                })
                .verifyComplete();
    }

    @Test
    void getItem_throwException() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Mono.empty());

        StepVerifier.create(itemService.getItem(ITEM_ID))
                .verifyError(IllegalArgumentException.class);
    }
}