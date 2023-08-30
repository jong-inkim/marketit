package com.marketit.order.repository;

import com.marketit.order.domain.Item;
import com.marketit.order.dto.OrderItemResponseDto;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;
import com.marketit.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositorySupportTest {

    @Autowired OrderRepositorySupport orderRepositorySupport;
    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;


    @BeforeEach
    void regist_test_item() {
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

        List<OrderRequestDto> dtos = new ArrayList<>();
        dtos.add(new OrderRequestDto(1L, 10));
        dtos.add(new OrderRequestDto(2L, 12));
        orderService.receiveOrder(dtos);

        List<OrderRequestDto> dtos2 = new ArrayList<>();
        dtos2.add(new OrderRequestDto(1L, 10));
        dtos2.add(new OrderRequestDto(2L, 12));
        dtos2.add(new OrderRequestDto(3L, 5));
        dtos2.add(new OrderRequestDto(4L, 3));
        orderService.receiveOrder(dtos2);
    }

    @Test
    @Transactional
    void findAllTest() {
        List<OrderResponseDto> orders = orderRepositorySupport.findAll();
        for (OrderResponseDto order : orders) {
            log.info("order.getId = {}", order.getId());
            log.info("order.getOrderStatus = {}", order.getOrderStatus());
            log.info("order.getOrderAt = {}", order.getOrderAt());
            for (OrderItemResponseDto orderItemResponseDto : order.getOrderItemResponseDtos()) {
                log.info("item name = {}", orderItemResponseDto.getName());
            }
        }
    }
}