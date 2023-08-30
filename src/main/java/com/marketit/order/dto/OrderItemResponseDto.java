package com.marketit.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDto {

    @Schema(description = "상품 ID", defaultValue = "1")
    private Long itemId;
    @Schema(description = "상품명", defaultValue = "itemA")
    private String name;
    @Schema(description = "주문 갯수", defaultValue = "1")
    private int count;

    @Builder
    public OrderItemResponseDto(Long itemId, String name, int count) {
        this.itemId = itemId;
        this.name = name;
        this.count = count;
    }
}
