package ru.azmeev.intershop.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import ru.azmeev.intershop.model.entity.ItemEntity;

public interface ExtraItemRepository {
    Flux<ItemEntity> filterItems(String text, Pageable pageable);
}
