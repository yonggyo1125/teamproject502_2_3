package com.joyfarm.farmstival.activity.entities;


import com.joyfarm.farmstival.activity.constants.AM_PM;
import com.joyfarm.farmstival.activity.constants.Status;
import com.joyfarm.farmstival.global.entities.BaseEntity;
import com.joyfarm.farmstival.member.entities.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq; //자동증감번호

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member; //한명이 여러 예약 가능

    @ManyToOne(fetch = FetchType.EAGER)
    private Activity activity;
    
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Status status; //null->상태 변경 시 메일

    @Column(length = 40, nullable = false)
    private String name; //예약자명

    @Column(length = 100, nullable = false)
    private String email; //예약자 이메일

    @Column(length = 20, nullable = false)
    private String mobile; //예약자 전화번호

    @Column(length = 80)
    private String townName; //체험마을명

    @Column
    private String activityName; //체험프로그램명

    @Column(length = 100)
    private String doroAddress; //도로명 주소

    @Column(length = 40)
    private String ownerName; //대표자명

    @Column(length = 20)
    private String ownerTel; //대표자 전화번호
    
    private LocalDate rDate; //예약 날짜

    @Column(length = 2)
    @Enumerated(EnumType.STRING) 
    private AM_PM ampm; //오전, 오후 구분
    
    private int persons = 1; //예약 인원수, 기본값 1명 반영 안됨..
}
