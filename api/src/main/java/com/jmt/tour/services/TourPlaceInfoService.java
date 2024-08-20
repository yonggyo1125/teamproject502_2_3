package com.jmt.tour.services;

import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.tour.controllers.TourPlaceSearch;
import com.jmt.tour.entities.QTourPlace;
import com.jmt.tour.entities.TourPlace;
import com.jmt.tour.exceptions.TourPlaceNotFoundException;
import com.jmt.tour.repositories.TourPlaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class TourPlaceInfoService {

    private final TourPlaceRepository repository;
    private final HttpServletRequest request;

    public ListData<TourPlace> getList(TourPlaceSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        /* 검색 처리 S */
        QTourPlace tourPlace = QTourPlace.tourPlace;
        BooleanBuilder andBuilder = new BooleanBuilder();

        String sopt = search.getSopt();
        String skey = search.getSkey();
        String sido = search.getSido();
        String sigungu = search.getSigungu();
        System.out.printf("sido=%s, sigungu=%s%n", sido, sigungu);
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL"; // 통합검색이 기본
        // 키워드가 있을때 조건별 검색
        if (StringUtils.hasText(skey) && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             * ALL - 통합 검색 - title, tel, address, description
             * TITLE, TEL, ADDRESS, DESCRIPTION
             */
            sopt = sopt.trim();
            skey = skey.trim();

            BooleanExpression condition = null;
            if (sopt.equals("ALL")) { // 통합 검색
                condition = tourPlace.title.concat(tourPlace.tel).concat(tourPlace.address)
                        .concat(tourPlace.description).contains(skey);

            } else if (sopt.equals("TITLE")) { // 여행지 명
                condition = tourPlace.title.contains(skey);

            } else if (sopt.equals("TEL")) { // 여행지 연락처
                skey = skey.replaceAll("\\D", ""); // 숫자만 남긴다.
                condition = tourPlace.tel.contains(skey);

            } else if (sopt.equals("ADDRESS")) { // 여행지 주소
                condition = tourPlace.address.contains(skey);

            } else if (sopt.equals("DESCRIPTION")) { // 여행지 설명
                condition = tourPlace.description.contains(skey);

            }

            if (condition != null) andBuilder.and(condition);
        }

        // 시도 검색
        if (sido != null && StringUtils.hasText(sido.trim())) {
            String sido2 = "";
            if (sido.equals("서울특별시")) sido2 = "서울";
            else if (sido.equals("인천광역시")) sido2 = "인천";
            else if (sido.equals("경기도")) sido2 = "경기";
            else if (sido.equals("강원도") || sido.equals("강원특별자치도")) sido2 = "강원";
            else if (sido.equals("충청북도")) sido2 = "충북";
            else if (sido.equals("충청남도")) sido2 = "충남";
            else if (sido.equals("경상북도")) sido2 = "경북";
            else if (sido.equals("경상남도")) sido2 = "경남";
            else if (sido.equals("전라북도")) sido2 = "전북";
            else if (sido.equals("전라남도")) sido2 = "전남";
            else if (sido.equals("대전광역시")) sido2 = "대전";
            else if (sido.equals("세종특별자치시")) sido2 = "세종";
            else if (sido.equals("제주특별자치도")) sido2 = "제주";
                
            BooleanBuilder orBuilder = new BooleanBuilder();
            andBuilder.and(orBuilder.or(tourPlace.address.contains(sido.trim())
                    .or(tourPlace.address.contains(sido2.trim()))));

            // 시군구 검색
            if (sigungu != null && StringUtils.hasText(sigungu.trim())) {
                andBuilder.and(tourPlace.address.contains(sigungu.trim()));
            }
        } // endif - sido

        /* 검색 처리 E */

        // 페이징 및 정렬 처리
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        // 데이터 조회
        Page<TourPlace> data = repository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        List<TourPlace> items = data.getContent();

        return new ListData<>(items, pagination);
    }

    /**
     * 상세 조회
     *
     * @param seq
     * @return
     */
    public TourPlace get(Long seq) {

        TourPlace item = repository.findById(seq).orElseThrow(TourPlaceNotFoundException::new);

        // 추가 데이터 처리

        return item;
    }
}
