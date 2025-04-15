package ru.azmeev.intershop.showcase.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;

public interface ExtraItemRepository {
    Flux<ItemEntity> filterItems(String text, Pageable pageable);
}
