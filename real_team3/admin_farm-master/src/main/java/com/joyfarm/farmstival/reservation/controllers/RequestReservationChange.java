package com.joyfarm.farmstival.reservation.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestReservationChange {
    @NotNull
    private List<Long> seq; // 예약 번호

    @NotBlank
    private String status; // 변경 상태
}
