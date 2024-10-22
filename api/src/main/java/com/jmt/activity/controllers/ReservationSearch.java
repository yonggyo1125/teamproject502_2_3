package com.jmt.activity.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationSearch extends CommonSearch {

    private LocalDate sDate; // 예약 검색 시작일
    private LocalDate eDate; // 예약 검색 종료일

    private List<Long> memberSeqs; // 회원번호 목록
}
