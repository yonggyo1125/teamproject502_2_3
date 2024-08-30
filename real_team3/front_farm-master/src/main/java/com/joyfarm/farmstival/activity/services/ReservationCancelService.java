package com.joyfarm.farmstival.activity.services;

import com.joyfarm.farmstival.activity.constants.Status;
import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationCancelService {

    private final ReservationRepository reservationRepository;
    private final ReservationStatusService statusService;
    private final ReservationInfoService infoService;

    public Reservation cancel(Long seq) {
        Reservation data = infoService.get(seq);

        data.setDeletedAt(LocalDateTime.now());

        //취소 상태로 변경
        statusService.change(data.getSeq(), Status.CANCEL);

        reservationRepository.saveAndFlush(data);

        return data;
    }
}