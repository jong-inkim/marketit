package com.marketit.order.dto;

import com.marketit.order.domain.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    @Schema(description = "주문 ID")
    private Long id;
    @Schema(description = "주문한 상품리스트")
    private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
    @Schema(description = "주문 상태")
    private OrderStatus orderStatus;
    @Schema(description = "주문 시간")
    private LocalDateTime orderAt;

    @Builder
    public OrderResponseDto(Long id, List<OrderItemResponseDto> orderItemResponseDtos, OrderStatus orderStatus, LocalDateTime orderAt) {
        this.id = id;
        this.orderItemResponseDtos = orderItemResponseDtos;
        this.orderStatus = orderStatus;
        this.orderAt = orderAt;
    }
}
