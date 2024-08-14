package com.jmt.tour.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

@Data
public class TourPlaceSearch extends CommonSearch {


    // SIDO : 시도
    private String sido;

    // SIDO + SIGUNGU : 시도 + 시군구
    private String sigungu;

}
