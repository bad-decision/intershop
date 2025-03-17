package ru.azmeev.intershop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.azmeev.intershop.model.entity.CartItemEntity;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.CartItemRepository;
import ru.azmeev.intershop.repository.ItemRepository;
import ru.azmeev.intershop.service.impl.ItemServiceImpl;
import ru.azmeev.intershop.web.dto.ItemDto;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.web.mapper.ItemMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

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
        testCartItem.setItem(testItem);
    }

    @Test
    void searchItems_success() {
        Page<ItemEntity> page = new PageImpl<>(List.of(testItem));
        doReturn(page).when(itemRepository).filterItems(any(), any());
        doReturn(List.of(testCartItem)).when(cartItemRepository).findByItems(any());

        ItemFilterDto filter = ItemFilterDto.builder()
                .pageNumber(1)
                .pageSize(1)
                .search("text")
                .build();
        Page<ItemDto> result = itemService.searchItems(filter);
        assertEquals(1, result.getTotalElements());
        assertEquals(testCartItem.getCount(), result.getContent().get(0).getCount());
    }

    @Test
    void getItem_success() {
        doReturn(Optional.of(testItem)).when(itemRepository).findById(ITEM_ID);
        doReturn(Optional.empty()).when(cartItemRepository).findByItem(any());

        ItemDto item = itemService.getItem(ITEM_ID);
        assertEquals(testItem.getId(), item.getId());
        assertEquals(0, item.getCount());
    }

    @Test
    void getItem_throwException() {
        doReturn(Optional.empty()).when(itemRepository).findById(ITEM_ID);
        assertThrows(IllegalArgumentException.class, () -> itemService.getItem(ITEM_ID));
    }
}