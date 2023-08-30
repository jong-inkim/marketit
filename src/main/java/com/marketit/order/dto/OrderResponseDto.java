package com.marketit.order.dto;

import com.marketit.order.domain.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
    private OrderStatus orderStatus;
    private LocalDateTime orderAt;

    @Builder
    public OrderResponseDto(Long id, List<OrderItemResponseDto> orderItemResponseDtos, OrderStatus orderStatus, LocalDateTime orderAt) {
        this.id = id;
        this.orderItemResponseDtos = orderItemResponseDtos;
        this.orderStatus = orderStatus;
        this.orderAt = orderAt;
    }
}
