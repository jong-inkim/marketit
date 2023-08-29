package com.marketit.order.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private OrderStatus orderStatus;

    private LocalDateTime orderAt;

    public Order(List<OrderItem> orderItems) {
        this.orderStatus = OrderStatus.RECEIPT;
        this.orderAt = LocalDateTime.now();
        this.orderItems = orderItems;
    }

    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETE;
    }
}
