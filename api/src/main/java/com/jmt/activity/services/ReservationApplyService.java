package com.jmt.activity.services;

import com.jmt.activity.entities.Reservation;
import com.jmt.activity.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationApplyService {
    private final ReservationRepository reservationRepository;

    public Reservation apply() {

        return null;
    }
}
