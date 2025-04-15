package ru.azmeev.intershop.showcase.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.ItemEntity;

import java.util.List;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<ItemEntity, Long>,
        ExtraItemRepository {
    @Query("""
            SELECT COUNT(*) FROM shop_item
            WHERE (title ILIKE CONCAT('%', :text, '%')
            OR description ILIKE CONCAT('%', :text, '%'))
            """)
    Mono<Long> countFilteredItems(String text);

    @Query("SELECT * from shop_item e where e.id in (:ids)")
    Flux<ItemEntity> findByIds(List<Long> ids);
}
