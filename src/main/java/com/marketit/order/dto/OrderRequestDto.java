package com.marketit.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long itemId;
    private int count;

    public OrderRequestDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
