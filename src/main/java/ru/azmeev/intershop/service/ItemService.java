package ru.azmeev.intershop.service;

import org.springframework.data.domain.Page;
import ru.azmeev.intershop.web.dto.ItemFilterDto;
import ru.azmeev.intershop.web.dto.ItemDto;

public interface ItemService {

    ItemDto getItem(Long id);

    Page<ItemDto> searchItems(ItemFilterDto filter);
}
