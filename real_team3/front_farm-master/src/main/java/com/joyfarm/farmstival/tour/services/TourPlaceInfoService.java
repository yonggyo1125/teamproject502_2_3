package com.joyfarm.farmstival.tour.services;

import com.joyfarm.farmstival.global.CommonSearch;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Pagination;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.tour.controllers.TourPlaceSearch;
import com.joyfarm.farmstival.tour.entities.QTourPlace;
import com.joyfarm.farmstival.tour.entities.TourPlace;
import com.joyfarm.farmstival.tour.exceptions.TourPlaceNotFoundException;
import com.joyfarm.farmstival.tour.repositories.TourPlaceRepository;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import com.joyfarm.farmstival.wishlist.services.WishListService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional // 엔티티 매니저 사용 시 필수
public class TourPlaceInfoService {

    private final TourPlaceRepository repository;
    private final HttpServletRequest request;
    private final Utils utils;
    private final WishListService wishListService;
    private final JPAQueryFactory queryFactory;

    public ListData<TourPlace> getList (TourPlaceSearch search) {
        int page = Math.max(search.getPage(), 1); // max함수 : 두 수를 비교해서 큰 수를 반환한다(1 미만의 수가 들어왔을 때는 1로 대체)
        int limit = search.getLimit(); // 한 페이지 당 보여줄 레코드 갯수
        limit = limit < 1 ? 20 : limit;

        /* 검색 처리 S */
        QTourPlace tourPlace = QTourPlace.tourPlace;
        BooleanBuilder andBuilder = new BooleanBuilder();

        String sopt = search.getSopt(); // 검색 옵션 ALL - 통합 검색
        String skey = search.getSkey(); // 검색 키워드 - 조건별 검색
        String sido = search.getSido();  // 시도 조회
        String sigungu = search.getSigungu(); // 시도에 종속적인 데이터이므로 시도가 있을 때 부가적으로 조회 가능

        sopt = StringUtils.hasText(sopt) ? sopt : "ALL"; // 통합 검색이 기본

        // 키워드가 있을 때 조건별 검색
        if (StringUtils.hasText(skey) && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             * ALL - 통합 검색 - title, tel, address, description
             * TITLE, TEL, ADDRESS, DESCRIPTION
             *
             */
            sopt = sopt.trim();
            skey = skey.trim();

            BooleanExpression condition = null;
            if(sopt.equals("ALL")) { // 통합 검색
                condition = tourPlace.title.concat(tourPlace.tel).concat(tourPlace.address).concat(tourPlace.description).contains(skey);

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
            /* Utils 에 처리한 시 짧은 명칭/긴 명칭 변환 가져옴 */
            String sido2 = utils.getShortSido(sido);
            sido = utils.getLongSido(sido2);

            BooleanBuilder orBuilder = new BooleanBuilder();
            andBuilder.and(orBuilder.or(tourPlace.address.contains(sido.trim())
                    .or(tourPlace.address.contains(sido2.trim()))));

            // 시군구 검색 (이것만 있으면 조회가 되지 않고, 꼭 시도가 있어야 함께 조회가 된다.)
            if (sigungu != null && StringUtils.hasText(sigungu.trim())) {
                andBuilder.and(tourPlace.sigungu.eq(sigungu.trim()));
            }
        } // endif - sido
        /* 검색 처리 E */

        /* 페이징 및 정렬 처리 */
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        /* 데이터 조회 */
        Page<TourPlace> data = repository.findAll(andBuilder, pageable);

        // Pagination 객체 만들기
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        List<TourPlace> items = data.getContent(); // 갯수에 맞게 조회된 데이터

        return new ListData<>(items, pagination);
    }

    /**
     * 여행지 상세 조회
     * @param seq
     * @return
     */
    public TourPlace get(Long seq) {
        TourPlace item = repository.findById(seq).orElseThrow(TourPlaceNotFoundException::new);

        return item;
    }

    /**
     * 찜한 여행지 목록
     * @param search
     * @return
     */
    public ListData<TourPlace> getWishList(CommonSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 10 : limit;

        List<Long> seqs = wishListService.getList(WishType.TOUR);
        if (seqs == null || seqs.isEmpty()) {
            return new ListData<>(); // 오류 방지를 위함
        }

        QTourPlace tourPlace = QTourPlace.tourPlace;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(tourPlace.seq.in(seqs));

        /* 페이징 및 정렬 처리 */
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        /* 데이터 조회 */
        Page<TourPlace> data = repository.findAll(andBuilder, pageable);

        // Pagination 객체 만들기
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        List<TourPlace> items = data.getContent(); // 갯수에 맞게 조회된 데이터

        return new ListData<>(items, pagination);
    }
}
