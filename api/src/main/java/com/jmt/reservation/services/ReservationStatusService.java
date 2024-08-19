package com.jmt.reservation.services;

import com.jmt.reservation.constants.ReservationStatus;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationStatusService {
    private final ReservationInfoService infoService;
    private final ReservationRepository repository;

    public void change(Long orderNo, ReservationStatus status) {
        Reservation reservation = infoService.get(orderNo);
        reservation.setStatus(status);

        repository.saveAndFlush(reservation);

        // 상태 변경에 따른 이메일 전송
    }
}
