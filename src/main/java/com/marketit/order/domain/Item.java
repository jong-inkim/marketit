package com.marketit.order.domain;

import com.marketit.order.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor
@BatchSize(size = 100)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int quantity;

    @Builder
    public Item(Long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void decreaseQuantity(int quantity) throws NotEnoughStockException {
        if (this.quantity - quantity < 0) {
            throw new NotEnoughStockException(this.name + "의 재고가 부족합니다.");
        }
        this.quantity = this.quantity - quantity;
    }
}
