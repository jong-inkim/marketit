package com.marketit.order.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "orders_id")
    private Order order;

    private int count;
    private int orderPrice;

    public OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice();
    }


}
