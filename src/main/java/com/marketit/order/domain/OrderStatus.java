package com.marketit.order.domain;

public enum OrderStatus {
    RECEIPT ("접수"),
    COMPLETE ("완료");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
