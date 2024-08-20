package com.jmt.activity.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RequestReservation {
    @NotNull
    private Long activitySeq; // 액티비티 등록 번호

    @NotBlank
    private String name; // 예약자 명

    @NotBlank
    private String email; // 예약자 이메일

    @NotBlank
    private String mobile; // 예약자 휴대전화번호

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate rDate; // 예약일

    @NotBlank
    private String ampm; // 오전 오후
}
