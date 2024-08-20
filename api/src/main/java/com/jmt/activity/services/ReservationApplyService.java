package com.jmt.activity.services;

import com.jmt.activity.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationApplyService {
    private final ReservationRepository reservationRepository;

}
