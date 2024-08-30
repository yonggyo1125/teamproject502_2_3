package com.joyfarm.farmstival.farmfarm.entities;

import com.joyfarm.farmstival.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Festival extends BaseEntity {
    @Id
    private Long seq; // 기본키 - contentId

    @Column(length=150, nullable = false)
    private String title; // 축제명

    @Column(length=20)
    private String cat1; // 분류명

    @Column(length=20)
    private String cat2; // 분류명

    @Column(length=20)
    private String cat3; // 분류명

    private Double latitude; // 위도
    private Double longitude; // 경도

    @Column(length=200)
    private String tel; // 연락처

    @Column(length=150)
    private String address; // 주소

    private String photoUrl1; // 사진
    private String photoUrl2;

    @Column(length=150)
    private String location; // 개최 장소

    @Lob
    private String content; // 축제 내용

    @Column(length=150)
    private String hostMain; // 주최 기관

    @Column(length=150)
    private String hostSub; // 주관 기관

    private LocalDate startDate; // 축제 시작일
    private LocalDate endDate; // 축제 종료일

    @Column(length=150)
    private String pageLink; // 홈페이지 주소
}
