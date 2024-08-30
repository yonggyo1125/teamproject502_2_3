package com.joyfarm.farmstival.board.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.board.controllers.RequestBoard;
import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.global.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardUpdateService {
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;

    //목록수정  updatelist
    public void update(String mode, List<BoardData> items) {
        mode = StringUtils.hasText(mode) ? mode : "edit";

        String url = utils.url("/board/admin/" + mode, "api-service");
        Map<String, List<BoardData>> params = new HashMap<>();
        params.put("items", items);

        try {
            String jsonBody = om.writeValueAsString(params);
            HttpHeaders headers = utils.getCommonHeaders("POST");
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, request, String.class);

            System.out.println(response);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //게시물 개별 수정
    public boolean updateSave(String mode, RequestBoard form) {
        mode = StringUtils.hasText(mode) ? mode : "edit";

        String url = utils.url("/board/admin/edit/" + form.getSeq(), "api-service");

        try{
            String jsonBody = om.writeValueAsString(form);
            HttpHeaders headers = utils.getCommonHeaders("POST");
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, request, String.class);

            System.out.println(response);

            return response.getStatusCode().is2xxSuccessful();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }

    }
}
