package com.joyfarm.farmstival.tour.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import lombok.Data;

@Data
public class TourPlaceSearch extends CommonSearch {
    /**
     * ALL - 통합 검색 - title, tel, address, description
     * TITLE, TEL, ADDRESS, DESCRIPTION
     *
     */

    // SIDO : 시도
    private String sido;

    // SIDO + SIGUNGU : 시도 + 시군구
    private String sigungu;

}
