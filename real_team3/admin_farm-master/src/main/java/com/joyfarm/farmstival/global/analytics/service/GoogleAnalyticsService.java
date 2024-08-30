//package com.joyfarm.farmstival.global.analytics.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class GoogleAnalyticsService {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${google.client-id}")
//    private String clientId;
//
//    @Value("${google.client-secret}")
//    private String clientSecret;
//
//    @Value("${google.refresh-token}")
//    private String refreshToken;
//
//    @Value("${google.token-uri}")
//    private String tokenUri;
//
//    @Value("${google.property-id}")
//    private String propertyId;
//
//    public GoogleAnalyticsService(RestTemplateBuilder builder) {
//        this.restTemplate = builder.build();
//    }
//
//    // Access Token 발급
//    public String getAccessToken() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("refresh_token", refreshToken);
//        params.add("grant_type", "refresh_token");
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            return response.getBody().get("access_token").toString();
//        } else {
//            throw new RuntimeException("Failed to get access token");
//        }
//    }
//
//    // 주간 및 월간 데이터를 가져오는 메서드
//    public Map<String, Object> getVisitorData() {
//        String accessToken = getAccessToken();
//
//        // GA4 API 요청 URL
//        String url = "https://analyticsdata.googleapis.com/v1beta/properties/" + propertyId + ":runReport";
//
//        // HTTP Headers 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // 일주일치 데이터 요청
//        Map<String, Object> weeklyRequestBody = new HashMap<>();
//        weeklyRequestBody.put("dateRanges", List.of(Map.of("startDate", "2024-08-14", "endDate", "today")));
//        weeklyRequestBody.put("metrics", List.of(Map.of("name", "activeUsers")));
//        weeklyRequestBody.put("dimensions", List.of(Map.of("name", "date")));
//
//        HttpEntity<Map<String, Object>> weeklyEntity = new HttpEntity<>(weeklyRequestBody, headers);
//
//        // 한 달치 데이터 요청
//        Map<String, Object> monthlyRequestBody = new HashMap<>();
//        monthlyRequestBody.put("dateRanges", List.of(Map.of("startDate", "2024-07-21", "endDate", "today")));
//        monthlyRequestBody.put("metrics", List.of(Map.of("name", "activeUsers")));
//        monthlyRequestBody.put("dimensions", List.of(Map.of("name", "date")));
//
//        HttpEntity<Map<String, Object>> monthlyEntity = new HttpEntity<>(monthlyRequestBody, headers);
//
//        // 페이지별 조회수 데이터 요청
//        Map<String, Object> pageViewsRequestBody = new HashMap<>();
//        pageViewsRequestBody.put("dateRanges", List.of(Map.of("startDate", "7daysAgo", "endDate", "today")));
//        pageViewsRequestBody.put("metrics", List.of(Map.of("name", "screenPageViews"))); // 페이지뷰 메트릭
//        pageViewsRequestBody.put("dimensions", List.of(Map.of("name", "pagePath"))); // 페이지 경로 차원
//
//        HttpEntity<Map<String, Object>> pageViewsEntity = new HttpEntity<>(pageViewsRequestBody, headers);
//
//        // API 호출
//        ResponseEntity<Map> weeklyResponse = restTemplate.postForEntity(url, weeklyEntity, Map.class);
//        ResponseEntity<Map> monthlyResponse = restTemplate.postForEntity(url, monthlyEntity, Map.class);
//        ResponseEntity<Map> pageViewsResponse = restTemplate.postForEntity(url, pageViewsEntity, Map.class);
//
//        // 결과를 맵에 저장
//        Map<String, Object> result = new HashMap<>();
//        result.put("weeklyData", weeklyResponse.getBody());
//        result.put("monthlyData", monthlyResponse.getBody());
//        result.put("pageViewsData", pageViewsResponse.getBody());
//
//        return result;
//    }
//}
//
