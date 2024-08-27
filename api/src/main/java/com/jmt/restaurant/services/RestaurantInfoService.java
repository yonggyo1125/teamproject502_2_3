package com.jmt.restaurant.services;

import com.jmt.global.CommonSearch;
import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.restaurant.controllers.RestaurantSearch;
import com.jmt.restaurant.entities.QRestaurant;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.exceptions.RestaurantNotFoundException;
import com.jmt.restaurant.repositories.RestaurantRepository;
import com.jmt.wishlist.constants.WishType;
import com.jmt.wishlist.services.WishListService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantInfoService {
    private final HttpServletRequest request;
    private final RestaurantRepository repository;
    private final JPAQueryFactory queryFactory;
    private final WishListService wishListService;

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

        items.forEach(this::addInfo);

        long total = repository.count(andBuilder); // 조회된 전체 갯수

        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    /**
     * 식당 정보 조회
     *
     * @param rstrId
     * @return
     */
    public Restaurant get(Long rstrId) {
        Restaurant item = repository.findById(rstrId).orElseThrow(RestaurantNotFoundException::new);

        // 추가 데이터 처리
        addInfo(item);

        return item;
    }

    /**
     * 찜하기 목록
     *
     * @return
     */
    public ListData<Restaurant> getWishList(CommonSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 10 : limit;
        int offset = (page - 1) * limit;

        List<Long> rstrIds = wishListService.getList(WishType.RESTAURANT);
        if (rstrIds == null || rstrIds.isEmpty()) {
            return new ListData<>();
        }

        QRestaurant restaurant = QRestaurant.restaurant;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(restaurant.rstrId.in(rstrIds));

        List<Restaurant> items = queryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.images)
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(restaurant.createdAt.desc())
                .fetch();

        items.forEach(this::addInfo);

        long total = repository.count(andBuilder); // 조회된 전체 갯수

        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    /**
     * 추가 데이터 처리 
     *  1. 예약가능 일자
     *  2. 예약가능 요일 
     *  3. 예약가능 시간대
     * @param item
     */
    private void addInfo(Restaurant item) {
        // 운영 정보로 예약 가능 데이터 처리 S
        String operInfo = item.getBsnsTmCn();
        try {
            if (operInfo == null || !StringUtils.hasText(operInfo.trim())) {
                operInfo = "매일 12:00~22:00";
            }

            if (operInfo != null && StringUtils.hasText(operInfo.trim())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                Map<String, List<LocalTime>> availableTimes = new HashMap<>();
                boolean[] yoils = new boolean[7]; // 0~6 true, false
                for (String oper : operInfo.split(",\\s*")) {
                    String[] _oper = oper.split("\\s+");
                    String yoil = _oper[0];
                    String time = _oper[1];

                    if (yoil.equals("평일")) {
                        for (int i = 1; i < 6; i++) {
                            yoils[i] = true;
                        }
                    } else if (yoil.equals("매일")) {
                        for (int i = 0; i < yoils.length; i++) {
                            yoils[i] = true;
                        }
                    } else if (yoil.equals("토요일")) {
                        yoils[6] = true;
                    } else if (yoil.equals("일요일")) {
                        yoils[0] = true;
                    } else if (yoil.equals("주말")) {
                        yoils[0] = yoils[6] = true;
                    }

                    // 예약 가능 시간대 S
                    String[] _time = time.split("~");
                    LocalTime sTime = LocalTime.parse(_time[0], formatter);
                    LocalTime eTime = LocalTime.parse(_time[1], formatter);

                    Duration du = Duration.between(sTime, eTime);
                    int hours = (int) du.getSeconds() / (60 * 60);

                    List<LocalTime> _availableTimes = new ArrayList<>();
                    for (int i = 0; i <= hours; i++) {
                        LocalTime t = sTime.plusHours(i);
                        // 예약시간 가능 시간에 + 1시간이 종료 시간를 지난 경우는 X
                        if (t.plusHours(1L).isAfter(eTime)) {
                            continue;
                        }
                        _availableTimes.add(t);
                    }

                    availableTimes.put(yoil, _availableTimes);
                    // 예약 가능 시간대 E
                }

                // 예약 가능 시간대
                item.setAvailableTimes(availableTimes);

                item.setAvailableWeeks(yoils); // 예약 가능 요일

                List<LocalDate> availableDates = new ArrayList<>();
                LocalDate startDate = LocalDate.now().plusDays(1L);
                LocalDate endDate = startDate.plusMonths(1L).minusDays(1L);

                Period period = Period.between(startDate, endDate);
                int days = period.getDays() + 1;

                for (int i = 0; i <= days; i++) {
                    LocalDate date = startDate.plusDays(i);
                    int yoil = date.getDayOfWeek().getValue() % 7;
                    if (yoils[yoil]) { // 영업 가능 요일인 경우
                        availableDates.add(date);
                    }
                }

                item.setAvailableDates(availableDates);


            } // endif

            // 운영 정보로 예약 가능 데이터 처리 E
        } catch (Exception e) {
            System.out.println(operInfo);
        }
    }
}
