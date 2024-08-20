package com.jmt.activity.entities;

import com.jmt.activity.constants.AM_PM;
import com.jmt.global.entities.BaseEntity;
import com.jmt.member.entities.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Reservation extends BaseEntity {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch=FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY)
    private Activity activity;

    private String name; // 예약자명

    private String email; // 예약자 이메일

    private String mobile; // 예약자 휴대전화번호

    private String townName; // 체험마을명
    
    private String activityName; // 체험프로그램명

    private String doroAddress; // 주소

    private String ownerName; // 대표자명

    private String ownerTel; // 대표자 전화번호

    private LocalDate rDate; // 예약일

    private AM_PM ampm; // 오전/오후 구분

    private int persons; // 예약인원수
}
