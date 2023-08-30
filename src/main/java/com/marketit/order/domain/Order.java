package com.marketit.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderAt;

    public Order(List<OrderItem> orderItems) {
        this.orderStatus = OrderStatus.RECEIPT;
        this.orderAt = LocalDateTime.now();
        this.orderItems.addAll(orderItems);
        orderItems.forEach(orderItem -> orderItem.setOrder(this));
    }

    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETE;
    }
}
