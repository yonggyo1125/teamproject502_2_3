package com.jmt.restaurant.entities;

import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    private Long rstrId;

    @Column(nullable = false)
    private String rstrNm; // 식당명

    private String rstrRdnmAdr; // 도로명 주소
    private String rstrLnnoAdres; // 구주소

    private Double rstrLa; // 위도
    private Double rstrLo; // 경도

    @Column(length=30)
    private String rstrTelNo; // 전화번호

    @Column(length=50)
    private String dbsnsStatmBzcndNm;  // 영업신고증업태명

    @Column(length=50)
    private String bsnsLcncNm; // 영업인허가명
    @Lob
    private String rstrIntrcnCont; // 음식점소개내용

    @Column(length=50)
    private String areaNm; // 지역명

    private Integer prdlSeatCnt; // 입식좌석수
    private Integer seatCnt; // 좌식좌석수

    private Boolean prkgPosYn; // 주차가능여부
    private Boolean wifiOfrYn; // Wifi 제공여부
    private Boolean dcrnYn; // 놀이방유무
    private Boolean petEntrnPosblYn; // 반려동물입장가능여부
    private Boolean fgggMenuOfrYn; // 다국어메뉴판제공여부

    @Column(length=60)
    private String tlromInfoCn; // 화장실정보내용

    @Column(length=2000)
    private String restdyInfoCn; // 휴무일정보내용

    @Column(length=2000)
    private String bsnsTmCn; // 영업시간내용

    private Boolean hmdlvSaleYn; // 택배판매유무
    private Boolean dsbrCvntlYn; // 배리어프리유무
    private Boolean delvSrvicYn; // 배달서비스유무

    @Column(length=50)
    private String rsrvMthdNm; // 예약방식명

    @Column(length=60)
    private String onlineRsrvInfoCn; // 온라인예약정보내용

    @Column(length=500)
    private String hmpgUrl; // 홈페이지 URL
    private Boolean kioskYn; // 키오스크 유무
    private Boolean mbPmamtYn; // 모바일페이먼트유무
    private Boolean smorderYn; // 스마트오더유무

    private String reprsntMenuNm; // 대표메뉴명

    @ToString.Exclude
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<RestaurantImage> images;
}
