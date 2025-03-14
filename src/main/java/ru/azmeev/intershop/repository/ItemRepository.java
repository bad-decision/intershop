package ru.azmeev.intershop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.azmeev.intershop.model.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    @Query("SELECT e FROM shop_Item e " +
            "WHERE (LOWER(e.title) LIKE CONCAT('%',LOWER(:text),'%') " +
            "OR LOWER(e.description) LIKE CONCAT('%',LOWER(:text),'%')) ")
    Page<ItemEntity> filterItems(String text, Pageable pageable);
}
