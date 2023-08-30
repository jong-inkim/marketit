package com.marketit.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long itemId;
    private String name;
    private int count;

    @Builder
    public OrderItemResponseDto(Long itemId, String name, int count) {
        this.itemId = itemId;
        this.name = name;
        this.count = count;
    }
}
