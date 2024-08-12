package com.jmt.farmfarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.rests.gov.api.ApiResult2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
@ActiveProfiles("test")
public class DataTest2 {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper om;

    private String serviceKey = "0Sgv0AKMOayMo1mD5VMSUVb%2BUcFF8gNjLps7PmIgLMFiD8YDLAZ9NDzmF7863XkTC0DkRBCSzqM4RROiej%2BrIw%3D%3D";

    @Test
    void test1() {
        String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchFestival1?MobileOS=AND&MobileApp=test&_type=json&eventStartDate=20240101&serviceKey=%s&pageNo=%d&numOfRows=1000", serviceKey, 1);

        ResponseEntity<ApiResult2> response = restTemplate.getForEntity(URI.create(url), ApiResult2.class);
        System.out.println(response);
    }
}
