package com.jmt.board.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.controllers.BoardDataSearch;
import com.jmt.board.entities.BoardData;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;

    public HttpHeaders getHeaders(String method) {
        HttpHeaders headers = new HttpHeaders();
        if (!List.of("GET", "DELETE").contains(method)) { // GET, DELETE 이외 방식은 모두 Body 데이터 있다.
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        return headers;
    }

    public ListData<BoardData> getList(BoardDataSearch search) {
        String url = utils.url("/board/admin", "api-service");

        try {
            String jsonParams = om.writeValueAsString(search);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
