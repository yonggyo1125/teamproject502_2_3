package com.jmt.activity.services;

import com.jmt.activity.controllers.ActivitySearch;
import com.jmt.activity.entities.Activity;
import com.jmt.activity.entities.QActivity;
import com.jmt.activity.exceptions.ActivityNotFoundException;
import com.jmt.activity.repositories.ActivityRepository;
import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ActivityInfoService {
    private final ActivityRepository activityRepository;
    private final HttpServletRequest request;

    /**
     * 액티비티 조회
     *
     * @param seq
     * @return
     */
    public Activity get(Long seq) {
        Activity item = activityRepository.findById(seq).orElseThrow(ActivityNotFoundException::new);

        // 추가 정보 처리
        addInfo(item);

        return item;
    }

    /**
     * 액티비티 목록 조회
     *
     * @return
     */
    public ListData<Activity> getList(ActivitySearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        int offset = (page - 1) * limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        /* 액티비티 검색 처리 S */
        QActivity activity = QActivity.activity;
        BooleanBuilder andBuilder = new BooleanBuilder();
        sopt = sopt != null && StringUtils.hasText(sopt.trim()) ? sopt.trim() : "ALL";
        if (skey != null && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             *      ALL - 통합 검색
             *            townName(체험 마을명), activityName (체험프로그램명)
             *            doroAddress(주소), ownerName(대표자명), ownerTel(대표자 전화번호)
             *      NAME - 예약자명, 대표자명
             *      MOBILE - 예약자 전화번호 + 대표자 전화번호
             *      ADDRESS - 도로명 주소
             *      ACTIVITY - 체험 마을명 + 체험프로그램명
             */

            skey = skey.trim();
            StringExpression expression = null;
            if (sopt.equals("ALL")) { // 통합 검색
                expression = activity.townName
                        .concat(activity.activityName)
                        .concat(activity.doroAddress)
                        .concat(activity.ownerName)
                        .concat(activity.ownerTel);
            } else if (sopt.equals("NAME")) {
                expression = activity.ownerName;
            } else if (sopt.equals("MOBILE")) {
                expression = activity.ownerTel;

            } else if (sopt.equals("ADDRESS")) {
                expression = activity.doroAddress;
            } else if (sopt.equals("ACTIVITY")) {
                expression = activity.activityName.concat(activity.townName);
            }

            if (expression != null) {
                andBuilder.and(expression.contains(skey));
            }
        }

        /* 액티비티 검색 처리 E */
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Activity> data = activityRepository.findAll(andBuilder, pageable);
        List<Activity> items = data.getContent();
        items.forEach(this::addInfo);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(items, pagination);
    }

    private void addInfo(Activity item) {
        LocalDate startDate = LocalDate.now();
        Map<LocalDate, boolean[]> availableDates = new HashMap<>();
        int hours = LocalTime.now().getHour();
        if (hours >= 12) { // 오후 시간이면 익일 예약 가능
            startDate = startDate.plusDays(1L);
            availableDates.put(startDate, new boolean[]{true, true});
        } else { // 당일 예약
            boolean[] time = hours > 8 ? new boolean[]{false, true} : new boolean[]{true, true};
            availableDates.put(startDate, time);
        }

        LocalDate endDate = startDate.plusMonths(1L).minusDays(1L);
        Period period = Period.between(startDate, endDate);
        int days = period.getDays() + 1;

        for (int i = 1; i <= days; i++) {
            availableDates.put(startDate.plusDays(i), new boolean[] {true, true});
        }

        item.setAvailableDates(availableDates);
    }
}
