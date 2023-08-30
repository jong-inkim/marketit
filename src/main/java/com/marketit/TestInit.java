package com.marketit;

import com.marketit.order.domain.Item;
import com.marketit.order.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class TestInit {

    private final ItemRepository itemRepository;

    public TestInit(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostConstruct
    public void setUp() {
        Random r = new Random();

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = Item.builder()
                    .name("item-" + i)
                    .price(r.nextInt(100) * 100)
                    .quantity(100)
                    .build();

            items.add(item);
        }
        itemRepository.saveAll(items);
    }
}
