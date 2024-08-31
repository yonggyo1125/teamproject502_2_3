package com.joyfarm.farmstival.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.script.AlertException;
import com.joyfarm.farmstival.global.rests.ApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationStatusService {
    private final ApiRequest apiRequest;
    private final ObjectMapper om;
    private final Utils utils;

    public void change(List<Long> seqs, String status) {
        if (seqs == null || seqs.isEmpty()) {
            throw new AlertException("변경할 예약을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        if (!StringUtils.hasText(status)) {
            throw new AlertException("예약상태를 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("seq", seqs);
        params.put("status", status);

        try {
            String url = utils.url("/myreservation/admin/status", "api-service");
            String jsonParams = om.writeValueAsString(params);

            ResponseEntity<Void> response = apiRequest.request(url, HttpMethod.PATCH, jsonParams, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new AlertException("변경에 실패하였습니다.", HttpStatus.BAD_REQUEST);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
