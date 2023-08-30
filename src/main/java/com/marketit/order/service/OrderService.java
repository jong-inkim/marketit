package com.marketit.order.service;

import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    public Long receiveOrder(List<OrderRequestDto> dtos);
    public Long completeOrder(Long orderId);

    public OrderResponseDto findByOrderId(Long orderId);

    public List<OrderResponseDto> findAll();
}
