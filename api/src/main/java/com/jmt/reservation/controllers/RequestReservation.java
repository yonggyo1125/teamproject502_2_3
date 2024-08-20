package com.jmt.reservation.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestReservation {
    private Long rstrId;
    private String name; // 예약자명
    private String email; // 예약자 이메일
    private String mobile; // 예약자 휴대전화번호

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate rDate; // 예약 날짜

    @JsonFormat(pattern="HH:mm")
    private LocalTime rTime; // 예약 시간

    private String payMethod; // 결제 수단

    private int persons; // 예약 인원수
}
