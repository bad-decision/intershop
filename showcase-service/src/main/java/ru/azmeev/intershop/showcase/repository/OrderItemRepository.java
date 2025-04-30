package ru.azmeev.intershop.showcase.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.azmeev.intershop.showcase.model.entity.OrderItemEntity;

import java.util.List;

@Repository
public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemEntity, Long> {
    Flux<OrderItemEntity> findByOrderId(Long orderId);

    @Query("SELECT * from shop_order_item e where e.order_id in (:orderIds)")
    Flux<OrderItemEntity> findByOrderIds(List<Long> orderIds);
}
