package com.joyfarm.farmstival.activity.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import lombok.Data;

@Data
public class ActivitySearch extends CommonSearch { //페이징 공통 적용

    private Double latitude;
    private Double longitude;
    private Integer radius = 1000;

    // SIDO : 시도
    private String sido;

    // SIDO + SIGUNGU : 시도 + 시군구
    private String sigungu;
}
