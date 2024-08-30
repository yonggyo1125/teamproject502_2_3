package com.joyfarm.farmstival.farmfarm.services;

import com.joyfarm.farmstival.farmfarm.controllers.FestivalSearch;
import com.joyfarm.farmstival.farmfarm.entities.Festival;
import com.joyfarm.farmstival.farmfarm.entities.QFestival;
import com.joyfarm.farmstival.farmfarm.exceptions.FestivalNotFoundException;
import com.joyfarm.farmstival.farmfarm.repositories.FestivalRepository;
import com.joyfarm.farmstival.global.CommonSearch;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Pagination;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import com.joyfarm.farmstival.wishlist.services.WishListService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
@Transactional // Querydsl 사용 (EntityManager 사용할 때는 필수)
public class FestivalInfoService {
    private final HttpServletRequest request;
    private final FestivalRepository repository;
    private final Utils utils;
    private final WishListService wishListService;
    //private final JPAQueryFactory queryFactory; //infoService 쓰게 되면 순환 참조 발생

    /* 축제 목록 조회 */
    public ListData<Festival> getList(FestivalSearch search){
        int page = Math.max(search.getPage(), 1); // max - 둘 중에 큰 수 반환 함수(0이 오면 1반환)
        int limit = search.getLimit(); // 한 페이지 당 보여줄 레코드 갯수!
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit; // 레코드 시작 위치 (1-20 / 21-40 / ...)

        /* 검색 처리 S */
        QFestival festival = QFestival.festival;
        BooleanBuilder andBuilder = new BooleanBuilder();

        String sopt = search.getSopt(); // 검색 옵션 (ALL - 통합 검색)
        String skey = search.getSkey(); // 검색 키워드
        String address = search.getAddress(); // 주소 검색

        sopt = StringUtils.hasText(sopt) ? sopt : "ALL"; // 통합 검색

        // 키워드 검색
        if (StringUtils.hasText(skey) && StringUtils.hasText(skey.trim())) {
            sopt = sopt.trim();
            skey = skey.trim();

            BooleanExpression condition = null;
            if (sopt.equals("ALL")){ // 통합 검색
                condition = festival.title.concat(festival.tel).concat(festival.address).concat(festival.content).contains(skey);

            } else if (sopt.equals("TITLE")) { // 축제명
                condition = festival.title.contains(skey);

            } else if (sopt.equals("TEL")) { // 축제 연락처
                skey = skey.replaceAll("\\D", ""); // TEL_2차가공_숫자만 남김
                condition = festival.tel.contains(skey);

            } else if (sopt.equals("LOCATION")) { // 축제 장소
                condition = festival.address.contains(skey);

            } else if (sopt.equals("CONTENT")) { // 축제 내용
                condition = festival.content.contains(skey);

            }
            if (condition != null) andBuilder.and(condition);
        }

        // 주소 검색 추가
        if (address != null && StringUtils.hasText(address.trim())) {
            if (address.contains("경기")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(festival.address.contains(address)).or(festival.address.contains("인천")));
            } else {
                andBuilder.and(festival.address.contains(utils.getLongSido(address)));
            }
        }

        /* 정렬 처리 S -> 추가중 ... ING...
        String sort = search.getSopt();

        PathBuilder<Festival> pathBuilder = new PathBuilder<>(Festival.class, "festival");
        OrderSpecifier orderSpecifier = null;
        Order order = Order.DESC;
        if(sort != null && StringUtils.hasText(sort.trim())){
            String[] _sort = sort.split("_");
            if(_sort[1].toUpperCase().equals("ASC")){
                order = Order.ASC;
            }

            orderSpecifier = new OrderSpecifier(order, pathBuilder.get(_sort[0]));
        }*/

        // 페이징 및 정렬 처리
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        // 데이터 조회
        Page<Festival> data = repository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        List<Festival> items = data.getContent(); // limit 갯수에 맞춰 출력

        return new ListData<>(items, pagination); // 페이징 가능한 목록 생성!
    }

    /* 축제 정보 조회 */
    public Festival get(Long seq){
        Festival item = repository.findById(seq).orElseThrow(FestivalNotFoundException::new);

        // 추가 데이터 처리

        return item;
    }

    /**
     * 찜한 여행지 목록
     * @param search
     * @return
     */
    public ListData<Festival> getWishList(CommonSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 10 : limit;

        List<Long> seqs = wishListService.getList(WishType.FESTIVAL);
        if (seqs == null || seqs.isEmpty()) {
            return new ListData<>(); // 오류 방지를 위함
        }

        QFestival festival =  QFestival.festival;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(festival.seq.in(seqs));

        /* 페이징 및 정렬 처리 */
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        /* 데이터 조회 */
        Page<Festival> data = repository.findAll(andBuilder, pageable);

        // Pagination 객체 만들기
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        List<Festival> items = data.getContent(); // 갯수에 맞게 조회된 데이터

        return new ListData<>(items, pagination);
    }
}
