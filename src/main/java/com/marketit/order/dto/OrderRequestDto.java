package com.marketit.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    @Schema(description = "주문 할 상품 ID", defaultValue = "1")
    private Long itemId;
    @Schema(description = "주문 할 갯수", defaultValue = "1")
    private int count;

    public OrderRequestDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
