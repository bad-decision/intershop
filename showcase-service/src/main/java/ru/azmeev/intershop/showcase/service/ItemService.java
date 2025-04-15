package ru.azmeev.intershop.showcase.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;
import ru.azmeev.intershop.showcase.web.dto.ItemFilterDto;

public interface ItemService {

    Mono<ItemDto> getItem(Long id);

    Mono<Page<ItemDto>> searchItems(ItemFilterDto filter);
}
