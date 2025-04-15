package ru.azmeev.intershop.showcase.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.azmeev.intershop.showcase.web.dto.ItemAddDto;
import ru.azmeev.intershop.showcase.web.dto.ItemDto;
import ru.azmeev.intershop.showcase.web.dto.ItemFilterDto;

import java.util.List;

public interface ItemService {

    Flux<ItemDto> addItems(List<ItemAddDto> items);

    Mono<ItemDto> getItem(Long id);

    Mono<Page<ItemDto>> searchItems(ItemFilterDto filter);
}
