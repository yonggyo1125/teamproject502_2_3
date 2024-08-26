package com.jmt.board.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.controllers.BoardDataSearch;
import com.jmt.board.entities.BoardData;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.member.entities.JwtToken;
import com.jmt.member.repositories.JwtTokenRepository;
import jakarta.servlet.http.HttpSession;
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
    private final JwtTokenRepository jwtTokenRepository;
    private final HttpSession session;

    public HttpHeaders getHeaders(String method) {

        JwtToken jwtToken =  jwtTokenRepository.findById(session.getId()).orElseThrow(UnAuthorizedException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.getToken());

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
