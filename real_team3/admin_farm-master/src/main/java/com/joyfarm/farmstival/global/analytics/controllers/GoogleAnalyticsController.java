//package com.joyfarm.farmstival.global.analytics.controllers;
//
//import com.joyfarm.farmstival.global.analytics.service.GoogleAnalyticsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequiredArgsConstructor
//public class GoogleAnalyticsController {
//    private final GoogleAnalyticsService googleAnalyticsService;
//
//    // JSON 데이터만 반환하는 API 엔드포인트
//    @GetMapping("/analytics/visitorInfo")
//    public Map<String, Object> getVisitorInfo() {
//        Map<String, Object> data = googleAnalyticsService.getVisitorData();
//        return data;
//    }
//}
