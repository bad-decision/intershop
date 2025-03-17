package ru.azmeev.intershop.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.azmeev.intershop.integration.IntegrationTestBase;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemRepositoryIT extends IntegrationTestBase {

    @Autowired
    private ItemRepository itemRepository;
    private final Pageable pageable = PageRequest.of(0, 5, Sort.unsorted());

    @Test
    void filterItems_mustReturnCorrectPage() {
        Page<ItemEntity> page = itemRepository.filterItems("ITEM1", pageable);
        assertEquals(2L, page.getTotalElements());
    }

    @Test
    void filterItems_mustReturnEmptyPage() {
        Page<ItemEntity> page = itemRepository.filterItems("NOT_EXIST_ITEM", pageable);
        assertEquals(0L, page.getTotalElements());
    }
}