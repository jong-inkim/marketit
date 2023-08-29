package com.marketit.order.service;

import com.marketit.order.domain.Item;
import com.marketit.order.domain.Order;
import com.marketit.order.domain.OrderStatus;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.repository.ItemRepository;
import com.marketit.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    private Long orderId = 0L;

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

        OrderRequestDto.ListDto dtos = new OrderRequestDto.ListDto();
        dtos.getOrderRequestDtos().add(new OrderRequestDto(1L, 10));
        dtos.getOrderRequestDtos().add(new OrderRequestDto(2L, 12));
        orderId = orderService.receiveOrder(dtos);
    }

    @Test
    void before_test() {
        Item item = itemRepository.findById(5L).orElseThrow();
        log.info("5 item name = {}", item.getName());
        log.info("items length = {}", itemRepository.count());
        assertThat(item.getName()).contains("item-");
    }

    @Test
    @Transactional
    void receive_order() {
        //given
        OrderRequestDto.ListDto dtos = new OrderRequestDto.ListDto();
        dtos.getOrderRequestDtos().add(new OrderRequestDto(1L, 10));
        dtos.getOrderRequestDtos().add(new OrderRequestDto(2L, 12));
        dtos.getOrderRequestDtos().add(new OrderRequestDto(3L, 5));
        dtos.getOrderRequestDtos().add(new OrderRequestDto(4L, 3));

        //when
        Long orderId = orderService.receiveOrder(dtos);
        log.info("orderId = {}", orderId);
        //then
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.RECEIPT);
    }

    @Test
    @Transactional
    void complete_order() {
        orderService.completeOrder(orderId);

        Order findOrder = orderRepository.findById(orderId).get();
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }
}