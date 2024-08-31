package com.joyfarm.farmstival.activity.services;


import com.joyfarm.farmstival.activity.constants.Status;
import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationStatusService {

    private final ReservationInfoService infoService;
    private final ReservationRepository repository;

    public void change(Long seq, Status status) {
        Reservation reservation = infoService.get(seq);
        Status prevStatus = reservation.getStatus();
        System.out.println(prevStatus);

        if(prevStatus != status) { //기존 상태와 동일하면 처리 x
            reservation.setStatus(status);
            repository.saveAndFlush(reservation);
            return;
        }


        // 예약 접수, 예약 취소 메일 전송
        String email = reservation.getEmail();
        // 이메일 전송 로직 추가 필요
    }

    public void change(List<Long> seqs, Status status) {
        seqs.forEach(seq -> change(seq, status));
    }
}
