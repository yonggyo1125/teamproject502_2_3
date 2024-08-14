package com.jmt.tour.services;

import com.jmt.global.ListData;
import com.jmt.tour.controllers.TourPlaceSearch;
import com.jmt.tour.entities.QTourPlace;
import com.jmt.tour.entities.TourPlace;
import com.jmt.tour.repositories.TourPlaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TourPlaceInfoService {

    private final TourPlaceRepository repository;

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


        /* 검색 처리 E */

        return null;
    }

    public TourPlace get(Long seq) {

        return null;
    }
}
