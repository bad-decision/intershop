package ru.azmeev.intershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.azmeev.intershop.model.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query("SELECT i FROM shop_CartItem i JOIN FETCH i.item WHERE i.item is not null ORDER BY i.createdDate desc")
    List<CartItemEntity> getCartItems();

    @Query("SELECT i FROM shop_CartItem i JOIN FETCH i.item WHERE i.item.id = :itemId")
    Optional<CartItemEntity> findByItem(Long itemId);

    @Query("SELECT i FROM shop_CartItem i JOIN FETCH i.item WHERE i.item.id in :itemIds")
    List<CartItemEntity> findByItems(List<Long> itemIds);
}
