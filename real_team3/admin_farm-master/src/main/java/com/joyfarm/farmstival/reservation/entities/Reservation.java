package com.joyfarm.farmstival.reservation.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joyfarm.farmstival.global.entities.BaseEntity;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.reservation.constants.AM_PM;
import com.joyfarm.farmstival.reservation.constants.Status;
import lombok.Data;

import java.time.LocalDate;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation extends BaseEntity {

    private Long seq; //자동증감번호

    private Member member; //한명이 여러 예약 가능

    private Activity activity;

    private Status status; //null->상태 변경 시 메일

    private String name; //예약자명

    private String email; //예약자 이메일

    private String mobile; //예약자 전화번호

    private String townName; //체험마을명

    private String activityName; //체험프로그램명

    private String doroAddress; //도로명 주소

    private String ownerName; //대표자명

    private String ownerTel; //대표자 전화번호

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate rDate; //예약 날짜

    private AM_PM ampm; //오전, 오후 구분
    
    private int persons; //예약 인원수, 기본값 1명 반영 안됨..
}
