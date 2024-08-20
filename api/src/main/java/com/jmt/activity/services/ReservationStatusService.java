package com.jmt.activity.services;

import com.jmt.activity.constants.Status;
import com.jmt.activity.entities.Reservation;
import com.jmt.activity.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("ReservationStatusService2")
@RequiredArgsConstructor
public class ReservationStatusService {

    private final ReservationInfoService infoService;
    private final ReservationRepository repository;

    public void change(Long seq, Status status) {
        Reservation reservation = infoService.get(seq);
        Status prevStatus = reservation.getStatus();
        if (prevStatus == status) { // 기존 상태와 동일하면 처리 X
            return;
        }

        reservation.setStatus(status);
        repository.saveAndFlush(reservation);

        // 예약 접수, 예약 취소 메일 전송
        String email = reservation.getEmail();
    }
}
