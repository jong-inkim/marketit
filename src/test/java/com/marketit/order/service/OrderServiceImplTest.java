package com.marketit.order.service;

import com.marketit.order.domain.Item;
import com.marketit.order.domain.Order;
import com.marketit.order.domain.OrderStatus;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;
import com.marketit.order.exception.NotEnoughStockException;
import com.marketit.order.repository.ItemRepository;
import com.marketit.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    private Long orderId = 0L;

    @BeforeAll
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
    @Rollback(value = false)
    void receive_order() {
        //given
        List<OrderRequestDto> dtos = new ArrayList<>();
        dtos.add(new OrderRequestDto(1L, 10));
        dtos.add(new OrderRequestDto(2L, 12));
        dtos.add(new OrderRequestDto(3L, 5));
        dtos.add(new OrderRequestDto(4L, 3));

        //when
        Long orderId = orderService.receiveOrder(dtos);
        log.info("orderId = {}", orderId);
        //then
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.RECEIPT);
    }

    @Test
    @Transactional
    void receive_order_not_enough_quantity() {
        //given
        List<OrderRequestDto> dtos = new ArrayList<>();
        dtos.add(new OrderRequestDto(1L, 200));

        //when
        assertThatThrownBy(() -> orderService.receiveOrder(dtos)).isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    @Transactional
    void complete_order() {
        orderService.completeOrder(orderId);;
        Order findOrder = orderRepository.findById(orderId).get();
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }

    @Test
    @Transactional
    void find_order() {
        OrderResponseDto responseDto = orderService.findByOrderId(orderId);
        log.info("responseDto.getId() = {}", responseDto.getId());
        log.info("responseDto.getOrderStatus() = {}", responseDto.getOrderStatus());
        log.info("responseDto.getOrderAt() = {}", responseDto.getOrderAt());
        responseDto.getOrderItemResponseDtos().forEach(dto -> {
            log.info("responseDto.getOrderItemResponseDtos().getItemId() = {}", dto.getItemId() );
            log.info("responseDto.getOrderItemResponseDtos().getCount() = {}", dto.getCount() );
            log.info("responseDto.getOrderItemResponseDtos().getName() = {}", dto.getName() );
        });

        Order order = orderRepository.findById(2L).get();
        log.info("order = {} ", order.getOrderStatus());
    }
}