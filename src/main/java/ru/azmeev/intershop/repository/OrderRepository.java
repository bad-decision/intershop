package ru.azmeev.intershop.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.azmeev.intershop.model.entity.OrderEntity;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {
}
