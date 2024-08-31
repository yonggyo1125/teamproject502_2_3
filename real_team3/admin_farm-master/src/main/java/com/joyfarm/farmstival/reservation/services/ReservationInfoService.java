package com.joyfarm.farmstival.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.rests.ApiRequest;
import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import com.joyfarm.farmstival.reservation.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ApiRequest apiRequest;
    private final ObjectMapper om;
    private final Utils utils;

    public ListData<Reservation> getList(ReservationSearch search) {
        String url = utils.url("/myreservation/admin/list", "api-service");
        ResponseEntity<String> response = apiRequest.request(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ListData<Reservation> data = om.readValue(response.getBody(), new TypeReference<>() {
                });
                return data;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
