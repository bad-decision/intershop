package ru.azmeev.intershop.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.web.dto.ItemDto;

public interface ItemService {

    Mono<ItemDto> getItem(Long id);

    Mono<Page<ItemDto>> searchItems(ItemFilterDto filter);
}
