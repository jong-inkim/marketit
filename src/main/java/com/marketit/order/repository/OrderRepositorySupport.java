package com.marketit.order.repository;

import com.marketit.order.dto.OrderItemResponseDto;
import com.marketit.order.dto.OrderResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.marketit.order.domain.QOrder.order;
import static com.marketit.order.domain.QOrderItem.orderItem;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
public class OrderRepositorySupport {
    private final EntityManager em;
    private final JPAQueryFactory query;

    @Autowired
    public OrderRepositorySupport(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    public List<OrderResponseDto> findAll() {
        List<OrderResponseDto> orderResponseDtos = query.select(order)
                .from(order)
                .leftJoin(order.orderItems, orderItem)
                .transform(groupBy(order.id).list(
                        Projections.fields(OrderResponseDto.class,
                                order.id,
                                order.orderStatus,
                                order.orderAt,
                                list(Projections.fields(OrderItemResponseDto.class,
                                        orderItem.item.id.as("itemId"),
                                        orderItem.item.name,
                                        orderItem.count)).as("orderItemResponseDtos"))
                ));

        return orderResponseDtos;
    }
}
