package com.joyfarm.farmstival.activity.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationSearch extends CommonSearch {

    private LocalDate sDate; //예약일 검색 시작일
    private LocalDate eDate; //예약일 검색 종료일

    private List<Long> memberSeqs; //회원번호 목록
}
