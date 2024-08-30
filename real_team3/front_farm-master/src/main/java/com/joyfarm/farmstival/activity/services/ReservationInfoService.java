package com.joyfarm.farmstival.activity.services;

import com.joyfarm.farmstival.activity.controllers.ReservationSearch;
import com.joyfarm.farmstival.activity.entities.QReservation;
import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.exceptions.ReservationNotFoundException;
import com.joyfarm.farmstival.activity.repositories.ReservationRepository;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Pagination;
import com.joyfarm.farmstival.global.exceptions.UnAuthorizedException;
import com.joyfarm.farmstival.member.MemberUtil;
import com.joyfarm.farmstival.member.entities.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationInfoService {

    private final JPAQueryFactory queryFactory; //목록 조회 패치 조인을 위함
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    /**
     * 예약 상세 정보 조회
     * @param seq
     * @return
     */
    public Reservation get(Long seq) {
        Reservation reservation = reservationRepository.findById(seq).orElseThrow(ReservationNotFoundException::new);

        //추가 정보 처리
        addInfo(reservation);

        return reservation;
    }

    public Reservation get(Long seq, boolean isMine) {
        Reservation reservation = get(seq);

        Member member = memberUtil.getMember();
        if (isMine && (!memberUtil.isLogin() || !member.getSeq().equals(reservation.getMember().getSeq()))) {
            throw new UnAuthorizedException();
        }

        return reservation;
    }

    //예약 목록 조회
    public ListData<Reservation> getList(ReservationSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        int offset = (page - 1) * limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        List<Long> memberSeqs = search.getMemberSeqs(); //회원번호로 조회(본인의 예약 정보만 조회)
        
        //검색 처리 S
        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder();
        
        sopt = sopt != null && StringUtils.hasText(sopt.trim()) ? sopt.trim() : "ALL"; //통합 검색
        if (skey != null && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             *  ALL- 통합 검색
             *   name(예약자명), email(이메일), mobile(예약자 전화번호)
             *   townName(체험 마을명), activityName(체험프로그램명)
             *   doroAddress(주소), ownerName(대표자명), ownerTel(대표자 전화번호)
             *  NAME - 예약자명, 대표자명
             *  EMAIL
             *  MOBILE - 예약자 전화번호 + 대표자 전화번호
             *  ADDRESS
             *  ACTIVITY - 체험마을명 + 체험 프로그램명
             */
            
            skey = skey.trim();
            StringExpression expression = null;
            if (sopt.equals("ALL")) { //통합 검색
                expression = reservation.townName
                        .concat(reservation.activityName)
                        .concat(reservation.doroAddress)
                        .concat(reservation.ownerName);
            } else if (sopt.equals("ADDRESS")) {
                expression = reservation.doroAddress;
            } else if (sopt.equals("ACTIVITY")) {
                expression = reservation.activityName.concat(reservation.townName);
            } if (expression != null) {
                andBuilder.and(expression.contains(skey)); //포함 조건
            }
        }
        
        //예약일 검색
        if (sDate != null) { //예약 시작일 검색
            andBuilder.and(reservation.rDate.goe(sDate)); //시작일보다 크거나 같다
            
        }
        if (eDate != null) { //예약 종료일 검색
            andBuilder.and(reservation.rDate.loe(eDate)); //종료일보다 작거나 같다
        }

        //회원번호 검색 처리
        if (memberSeqs != null && !memberSeqs.isEmpty()) {
            andBuilder.and(reservation.member.seq.in(memberSeqs));
        }

        //검색 처리 E
        
        //목록 데이터 가져오기
        List<Reservation> items = queryFactory.selectFrom(reservation)
                .leftJoin(reservation.member) //시점 데이터가 있기 때문에 필요할 때 활동 데이터를 불러오기로 함
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(reservation.createdAt.desc()) //예약 등록일자 기준 정렬
                .fetch();
        
        long total = reservationRepository.count(andBuilder);

        //pagination 객체 생성
        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    private void addInfo(Reservation reservation) {

    }

}
