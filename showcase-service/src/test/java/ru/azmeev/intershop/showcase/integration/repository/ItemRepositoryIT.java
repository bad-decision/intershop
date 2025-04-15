package ru.azmeev.intershop.showcase.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;
import ru.azmeev.intershop.showcase.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemRepositoryIT extends IntegrationTestBase {

    @Autowired
    private ItemRepository itemRepository;
    private final Pageable pageable = PageRequest.of(0, 5, Sort.unsorted());

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void filterItems_mustReturnCorrectList() {
        List<ItemEntity> data = itemRepository.filterItems("itEM1", pageable).collectList().block();
        assertEquals(2L, data.size());
    }

    @Test
    void countFilteredItems_mustReturnCorrectValue() {
        Long count = itemRepository.countFilteredItems("itEM1").block();
        assertEquals(2L, count);
    }

    @Test
    void filterItems_mustReturnEmptyList() {
        List<ItemEntity> data = itemRepository.filterItems("NOT_EXIST_ITEM", pageable).collectList().block();
        assertEquals(0L, data.size());
    }

    @Test
    void findByIds_mustReturnList() {
        List<ItemEntity> data = itemRepository.findByIds(List.of(1L, 2L)).collectList().block();
        assertEquals(2L, data.size());
    }
}