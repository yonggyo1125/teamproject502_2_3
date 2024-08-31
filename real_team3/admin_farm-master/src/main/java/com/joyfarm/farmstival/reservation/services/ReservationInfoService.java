package com.joyfarm.farmstival.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.rests.ApiRequest;
import com.joyfarm.farmstival.global.rests.JSONData;
import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import com.joyfarm.farmstival.reservation.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ApiRequest apiRequest;
    private final ObjectMapper om;
    private final Utils utils;

    public ListData<Reservation> getList(ReservationSearch search) {
        String url = utils.url("/myreservation/admin/list", "api-service");
        url += String.format("?page=%d&limit=%d", search.getPage(), search.getLimit());
        String sopt = search.getSopt();
        String skey = search.getSkey();
        if (sopt != null && skey != null) {
            url += String.format("&sopt=%s&skey=%s", sopt, skey);
        }

        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        if (sDate != null) {
            url += String.format("&sDate=%s", sDate);
        }

        if (eDate != null) {
            url += String.format("&eDate=%s", eDate);
        }
        ResponseEntity<JSONData> response = apiRequest.request(url, JSONData.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody().isSuccess()) {
            try {
                Object _data = response.getBody().getData();
                ListData<Reservation> data = om.readValue(om.writeValueAsString(_data), new TypeReference<>(){});

                return data;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
