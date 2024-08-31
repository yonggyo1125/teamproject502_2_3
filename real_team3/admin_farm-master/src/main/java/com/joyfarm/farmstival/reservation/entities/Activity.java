package com.joyfarm.farmstival.reservation.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Activity {

    private Long seq;

    private String townName; //체험마을명

    private String sido; //시도명

    private String sigungu; //시군구명

    private String division; //체험프로그램 구분

    private String activityName; //체험프로그램명

    private String facilityInfo; //보유시설정보

    private String townArea; //체험휴양마을면적
    
    private String townImage; //체험휴양마을 이미지

    private String doroAddress; //소재지도로명주소

    private String ownerName; //대표자명

    private String ownerTel; //대표전화번호

    private LocalDate createdDate; //지정일자
    
    private String wwwAddress; //홈페이지 주소
    
    private String manageAgency; //관리기관명
    
    private Double latitude; //위도
    
    private Double longitude; //경도
    
    private LocalDate uploadedData; //데이터 기준일자
    
    private String providerCode; //제공기관 코드
    
    private String providerName; //제공기관명
}
