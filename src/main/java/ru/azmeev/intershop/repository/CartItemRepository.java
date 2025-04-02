package ru.azmeev.intershop.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.model.entity.CartItemEntity;

import java.util.List;

@Repository
public interface CartItemRepository extends ReactiveCrudRepository<CartItemEntity, Long> {

    @Query("SELECT * FROM shop_cart_item i")
    Flux<CartItemEntity> getCartItems();

    @Query("SELECT * FROM shop_cart_item i WHERE i.item_id = :itemId")
    Mono<CartItemEntity> findByItem(Long itemId);

    @Query("SELECT * FROM shop_cart_item e WHERE e.item_id in (:itemIds)")
    Flux<CartItemEntity> findByItems(List<Long> itemIds);
}
