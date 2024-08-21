package com.jmt.reservation.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestReservation {

    @NotNull
    private Long rstrId; // 식당 등록 번호

    @NotBlank
    private String name; // 예약자명

    @NotBlank
    private String email; // 예약자 이메일

    @NotBlank
    private String mobile; // 예약자 휴대전화번호

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate rDate; // 예약 날짜

    @JsonFormat(pattern="HH:mm")
    private LocalTime rTime; // 예약 시간

    private String payMethod; // 결제 수단

    @Min(1)
    private int persons; // 예약 인원수
}
