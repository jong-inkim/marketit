package com.marketit.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long itemId;
    private int count;

    public OrderRequestDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    @Getter
    @Setter
    public static class ListDto {
        private List<OrderRequestDto> orderRequestDtos = new ArrayList<>();
    }
}
