package com.marketit.order.service;

import com.marketit.order.domain.Item;
import com.marketit.order.domain.Order;
import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    public Long receiveOrder(OrderRequestDto.ListDto orderListDto);
    public Long completeOrder(Long orderId);

    public OrderResponseDto findByOrderId(Long orderId);

    public List findAll();
}
