package com.jmt.restaurant.services;

import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.restaurant.controllers.RestaurantSearch;
import com.jmt.restaurant.entities.QRestaurant;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.repositories.RestaurantRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantInfoService {
    private final RestaurantRepository repository;
    private final JPAQueryFactory queryFactory;

    public ListData<Restaurant> getList(RestaurantSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit(); // 한페이지당 보여줄 레코드 갯수
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit; // 레코드 시작 위치

        // 검색 처리 S
        String sopt = search.getSopt(); // 검색 옵션 ALL - 통합 검색
        String skey = search.getSkey(); // 검색 키워드

        QRestaurant restaurant = QRestaurant.restaurant;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // 검색 처리 E

        List<Restaurant> items = queryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.images)
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(restaurant.createdAt.desc())
                .fetch();


        long total = repository.count(andBuilder); // 조회된 전체 갯수

        Pagination pagination = new Pagination(page, (int)total, 10, limit);

        return new ListData<>(items, pagination);
    }

}
