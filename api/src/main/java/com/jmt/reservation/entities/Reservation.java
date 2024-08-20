package com.jmt.reservation.entities;

import com.jmt.global.entities.BaseEntity;
import com.jmt.member.entities.Member;
import com.jmt.payment.constants.PayMethod;
import com.jmt.reservation.constants.ReservationStatus;
import com.jmt.restaurant.entities.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {
    @Id
    private Long orderNo = System.currentTimeMillis(); // 예약 접수 번호

    @Column(length=20)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.START;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rstrId")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(length=40, nullable = false)
    private String name; // 예약자명

    @Column(length=100, nullable = false)
    private String email; // 예약자 이메일

    @Column(length=20, nullable = false)
    private String mobile; // 예약자 휴대전화번호

    @Column(length=100, nullable = false)
    private String rName; // 식당명

    @Column(length=100, nullable = false)
    private String rAddress; // 식당 주소

    @Column(length=150)
    private String rTel; // 식당 연락처

    private LocalDateTime rDateTime; // 예약 일시

    private int price; // 1명당 예약금
    private int persons; // 예약 인원수

    @Column(length=20)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Lob
    private String payLog; // 결제 로그

    @Column(length=65)
    private String payTid; // PG 거래 ID(tid)

    @Column(length=40)
    private String payBankName; // 가상계좌 은행

    @Column(length=40)
    private String payBankAccount; // 가상계좌

    @Transient
    private int totalPayPrice; // 총 결제금액
}
