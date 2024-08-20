package com.jmt.activity.services;

import com.jmt.activity.entities.Reservation;
import com.jmt.activity.exceptions.ReservationNotFoundException;
import com.jmt.activity.repositories.ReservationRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationInfoService {
    private final JPAQueryFactory queryFactory;
    private final ReservationRepository reservationRepository;

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

    private void addInfo(Reservation reservation) {

    }
}
