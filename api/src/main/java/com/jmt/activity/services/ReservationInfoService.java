package com.jmt.activity.services;

import com.jmt.activity.controllers.ReservationSearch;
import com.jmt.activity.entities.QReservation;
import com.jmt.activity.entities.Reservation;
import com.jmt.activity.exceptions.ReservationNotFoundException;
import com.jmt.activity.repositories.ReservationRepository;
import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.member.MemberUtil;
import com.jmt.member.entities.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ReservationInfoService2")
@Transactional
@RequiredArgsConstructor
public class ReservationInfoService {
    private final JPAQueryFactory queryFactory;
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    /**
     * 예약 상세 정보
     *
     * @param seq
     * @return
     */
    public Reservation get(Long seq) {
        Reservation reservation = reservationRepository.findById(seq).orElseThrow(ReservationNotFoundException::new);

        // 추가 정보 처리
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

    public ListData<Reservation> getList(ReservationSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        int offset = (page - 1) * limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        List<Long> memberSeqs = search.getMemberSeqs(); // 회원번호로 조회

        /* 검색 처리 S */
        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder();

        sopt = sopt != null && StringUtils.hasText(sopt.trim()) ? sopt.trim() : "ALL";
        if (skey != null && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             *      ALL - 통합 검색
             *            name(예약자명), email(예약자 이메일), mobile(예약자 휴대전화번호)
             *            townName(체험 마을명), activityName (체험프로그램명)
             *            doroAddress(주소), ownerName(대표자명), ownerTel(대표자 전화번호)
             *      NAME - 예약자명, 대표자명
             *      EMAIL
             *      MOBILE - 예약자 전화번호 + 대표자 전화번호
             *      ADDRESS
             *      ACTIVITY - 체험 마을명 + 체험프로그램명
             */

            skey = skey.trim();
            StringExpression expression = null;
            if (sopt.equals("ALL")) { // 통합 검색
                expression = reservation.name.concat(reservation.email)
                        .concat(reservation.mobile)
                        .concat(reservation.townName)
                        .concat(reservation.activityName)
                        .concat(reservation.doroAddress)
                        .concat(reservation.ownerName)
                        .concat(reservation.ownerTel);
            } else if (sopt.equals("NAME")) {
                expression = reservation.name.concat(reservation.ownerName);
            } else if (sopt.equals("EMAIL")) {
                expression = reservation.email;
            } else if (sopt.equals("MOBILE")) {
                expression = reservation.mobile.concat(reservation.ownerTel);

            } else if (sopt.equals("ADDRESS")) {
                expression = reservation.doroAddress;
            } else if (sopt.equals("ACTIVITY")) {
                expression = reservation.activityName.concat(reservation.townName);
            }

            if (expression != null) {
                andBuilder.and(expression.contains(skey));
            }
        }

        // 예약일 검색
        if (sDate != null) { // 예약 시작일 검색
            andBuilder.and(reservation.rDate.goe(sDate));
        }

        if (eDate != null) { // 예약 종료일 검색
            andBuilder.and(reservation.rDate.loe(eDate));
        }

        // 회원번호 검색 처리
        if (memberSeqs != null && !memberSeqs.isEmpty()) {
            andBuilder.and(reservation.member.seq.in(memberSeqs));
        }

        /* 검색 처리 E */

        List<Reservation> items = queryFactory.selectFrom(reservation)
                .leftJoin(reservation.member)
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(reservation.createdAt.desc())
                .fetch();


        long total = reservationRepository.count(andBuilder);

        // int page, int total, int ranges, int limit, HttpServletRequest request
        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    private void addInfo(Reservation reservation) {



    }
}
