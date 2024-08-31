package com.joyfarm.farmstival.reservation.services;

import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.rests.ApiRequest;
import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ApiRequest apiRequest;

    public ListData getList(ReservationSearch search) {

        return null;
    }

}
