package ru.azmeev.intershop.showcase.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.model.entity.OrderEntity;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {
    Flux<OrderEntity> findByUserId(Long userId);

    @Query("SELECT * FROM shop_order i WHERE i.id = :orderId and i.user_id = :userId")
    Mono<OrderEntity> findByIdAndUserId(Long orderId, Long userId);
}
