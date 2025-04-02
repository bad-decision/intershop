package ru.azmeev.intershop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.azmeev.intershop.model.entity.ItemEntity;
import ru.azmeev.intershop.repository.ExtraItemRepository;

@Repository
@RequiredArgsConstructor
public class ExtraItemRepositoryImpl implements ExtraItemRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<ItemEntity> filterItems(String text, Pageable pageable) {
        Criteria criteria = Criteria.where("title").like("%" + text + "%").ignoreCase(true)
                .or("description").like("%" + text + "%").ignoreCase(true);

        return template.select(ItemEntity.class)
                .matching(Query.query(criteria).with(pageable))
                .all();
    }
}
