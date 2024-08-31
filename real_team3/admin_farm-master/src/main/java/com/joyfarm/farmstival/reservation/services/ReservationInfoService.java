package com.joyfarm.farmstival.reservation.services;

import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.rests.ApiRequest;
import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ApiRequest apiRequest;
    private final Utils utils;

    public ListData getList(ReservationSearch search) {
        String url = utils.url("/reservation/admin/list");
        ResponseEntity<String> response = apiRequest.request(url, String.class);
        System.out.println(response);
        return null;
    }

}
