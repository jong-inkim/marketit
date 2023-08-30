package com.marketit.order.service;

import com.marketit.order.domain.Item;
import com.marketit.order.domain.Order;
import com.marketit.order.domain.OrderItem;
import com.marketit.order.dto.OrderItemResponseDto;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;
import com.marketit.order.exception.ItemNotFoundException;
import com.marketit.order.exception.OrderNotFoundException;
import com.marketit.order.repository.ItemRepository;
import com.marketit.order.repository.OrderRepository;
import com.marketit.order.repository.OrderRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderRepositorySupport orderRepositorySupport;

    @Override
    @Transactional
    public Long receiveOrder(List<OrderRequestDto> dtos) {
        List<OrderItem> orderItems = new ArrayList<>();
        dtos.forEach(dto -> {
            Item findItem = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new ItemNotFoundException("아이템을 찾을 수 없습니다. itemId = " + dto.getItemId()));
            orderItems.add(new OrderItem(findItem, dto.getCount()));
            findItem.decreaseQuantity(dto.getCount());
        });

        Order order = new Order(orderItems);

        return orderRepository.save(order).getId();
    }

    @Override
    @Transactional
    public Long completeOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다. orderId = " + orderId));
        findOrder.completeOrder();

        return findOrder.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto findByOrderId(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다. orderId = " + orderId));
        List<OrderItem> orderItems = findOrder.getOrderItems();
        List<OrderItemResponseDto> orderItemResponseDtos = orderItems.stream().map(orderItem -> {
            Item item = orderItem.getItem();
            return OrderItemResponseDto.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .count(orderItem.getCount())
                    .build();
        }).collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(findOrder.getId())
                .orderItemResponseDtos(orderItemResponseDtos)
                .orderStatus(findOrder.getOrderStatus())
                .orderAt(findOrder.getOrderAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAll() {
        return orderRepositorySupport.findAll();
    }
}
