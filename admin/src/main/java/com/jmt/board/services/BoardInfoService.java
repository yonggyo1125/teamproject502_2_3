package com.jmt.board.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.controllers.BoardDataSearch;
import com.jmt.board.entities.BoardData;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;


    public ListData<BoardData> getList(BoardDataSearch search) {
        String url = utils.url("/board/admin", "api-service");

        HttpHeaders headers = utils.getCommonHeaders("GET");
        int page = search.getPage();
        int limit = search.getLimit();
        String sopt = Objects.requireNonNullElse(search.getSopt(), "");
        String skey = Objects.requireNonNullElse(search.getSkey(), "");
        String bid = Objects.requireNonNullElse(search.getBid(), "");
        String bids = search.getBids() == null ? "" :
                    search.getBids().stream()
                .map(s -> "bids=" + s).collect(Collectors.joining("&"));

        String sort = Objects.requireNonNullElse(search.getSort(), "");

        url += String.format("?page=%d&limit=%d&sopt=%s&skey=%s&bid=%s&sort=%s&%s", page, limit, sopt, skey, bid, sort, bids);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, String.class);


        return null;
    }
}
