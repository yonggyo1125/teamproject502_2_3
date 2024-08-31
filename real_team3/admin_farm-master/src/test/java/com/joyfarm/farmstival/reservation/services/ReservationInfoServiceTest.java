package com.joyfarm.farmstival.reservation.services;

import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
public class ReservationInfoServiceTest {
    @Autowired
    private ReservationInfoService infoService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Utils utils;
    @Test
    void test1() {

        ReservationSearch search = new ReservationSearch();
        //infoService.getList(search);

        HttpHeaders headers = new HttpHeaders();
        //JwtToken token = repository.findById(session.getId()).orElse(null);
       // System.out.println("token: " + token);
       // if (token != null) {
            //headers.setBearerAuth(token.getToken());
        //}
        headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDFAdGVzdC5vcmciLCJleHAiOjE3MjUwOTE1MTN9.6L0eW5AiXUKUE8gEvQVrBMVtpfBDBIn4DEhCt7YM0brQ0pzHraeVy2Cpd9oVqM_8cCOcO-TLC13PY4LcFpGezw");
        HttpEntity<String> entity = new HttpEntity<>(headers);
       // if (StringUtils.hasText(json)) {
        //    headers.setContentType(MediaType.APPLICATION_JSON);
        //    entity = new HttpEntity<>(json, headers);
        //} else {
        //    entity = new HttpEntity<>(headers);
        //}

        String url = utils.url("/myreservation/admin/list", "api-service");
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, String.class);


    }
}
