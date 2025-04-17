package ru.azmeev.intershop.showcase.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.repository.ItemRepository;
import ru.azmeev.intershop.showcase.service.CacheItemService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CacheItemServiceIT extends IntegrationTestBase {

    @Autowired
    private CacheItemService cacheItemService;
    @MockitoBean
    private ItemRepository itemRepository;

    private static final Long ITEM_ID = 1L;
    private ItemEntity testItem;

    @BeforeEach
    void prepare() {
        testItem = new ItemEntity();
        testItem.setId(ITEM_ID);
        testItem.setPrice(100.0);
        testItem.setTitle("ITEM");
    }

    @Test
    void findById_successGetDataFromCache() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Mono.just(testItem));
        ItemEntity item1 = cacheItemService.findById(ITEM_ID).block();
        ItemEntity item2 = cacheItemService.findById(ITEM_ID).block();

        assertEquals(item1.getId(), item2.getId());
        assertEquals(item1.getTitle(), item2.getTitle());
        assertEquals(item1.getDescription(), item2.getDescription());
        assertEquals(item1.getPrice(), item2.getPrice());
        verify(itemRepository, times(1)).findById(ITEM_ID);
    }

    @Test
    void findByIds_successGetDataFromCache() {
        when(itemRepository.findByIds(List.of(ITEM_ID))).thenReturn(Flux.just(testItem));
        List<ItemEntity> item1 = cacheItemService.findByIds(List.of(ITEM_ID)).block();
        List<ItemEntity> item2 = cacheItemService.findByIds(List.of(ITEM_ID)).block();

        assertEquals(item1.size(), 1);
        assertEquals(item2.size(), 1);
        assertEquals(item1.get(0).getId(), item2.get(0).getId());
        assertEquals(item1.get(0).getTitle(), item2.get(0).getTitle());
        assertEquals(item1.get(0).getDescription(), item2.get(0).getDescription());
        assertEquals(item1.get(0).getPrice(), item2.get(0).getPrice());
        verify(itemRepository, times(1)).findByIds(any());
    }

    @Test
    void searchItems_successGetDataFromCache() {
        String search = "search text";
        Pageable pageable = PageRequest.of(1, 1, Sort.unsorted());

        when(itemRepository.filterItems(any(), any())).thenReturn(Flux.just(testItem));
        when(itemRepository.countFilteredItems(any())).thenReturn(Mono.just(1L));
        Page<ItemEntity> page1 = cacheItemService.searchItems(search, pageable).block();
        Page<ItemEntity> page2 = cacheItemService.searchItems(search, pageable).block();

        assertEquals(page1.getTotalElements(), page2.getTotalElements());
        verify(itemRepository, times(1)).filterItems(any(), any());
        verify(itemRepository, times(1)).countFilteredItems(any());
    }
}