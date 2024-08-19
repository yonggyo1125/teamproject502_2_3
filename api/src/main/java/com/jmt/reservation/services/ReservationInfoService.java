package com.jmt.reservation.services;

import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.exceptions.ReservationNotFoundException;
import com.jmt.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;

    public Reservation get(Long orderNo) {
        Reservation item = reservationRepository.findById(orderNo).orElseThrow(ReservationNotFoundException::new);

        // 추가 데이터 처리

        return item;
    }

}
