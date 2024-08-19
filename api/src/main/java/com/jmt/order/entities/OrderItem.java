package com.jmt.order.entities;

import jakarta.persistence.*;
import lombok.*;
import org.g9project4.board.entities.BoardData;
import org.g9project4.global.entities.BaseEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    private Long seq;

    private String itemName; // 주문 상품명

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardData boardData;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private OrderInfo orderInfo;

    private int price; // 상품 단품 금액

    private int qty; // 주문 수량
}
