package com.marketit.order.service;

import com.marketit.order.domain.Item;
import com.marketit.order.domain.Order;
import com.marketit.order.domain.OrderItem;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.repository.ItemRepository;
import com.marketit.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Long receiveOrder(OrderRequestDto.ListDto orderListDto) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderListDto.getOrderRequestDtos().forEach(dto -> {
            Item findItem = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new RuntimeException());
            orderItems.add(new OrderItem(findItem, dto.getCount()));
            findItem.decreaseQuantity(dto.getCount());
        });

        Order order = new Order(orderItems);

        return orderRepository.save(order).getId();
    }

    @Override
    public Long completeOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());
        findOrder.completeOrder();

        return findOrder.getId();
    }

    @Override
    public Long findByOrderId(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List findAll() {
        return null;
    }
}
