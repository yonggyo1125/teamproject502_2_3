package com.joyfarm.farmstival.reservation.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationSearch extends CommonSearch {
    private LocalDate sDate; // 예약 시작일
    private LocalDate eDate; // 예약 종료일
    private String status; // ALL - 모든 상태, APPLY - 예약확정, CANCEL - 예약취소
}
