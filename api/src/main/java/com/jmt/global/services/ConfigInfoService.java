package com.jmt.global.services;

import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConfigInfoService {
    private final Utils utils;
    private final PasswordEncoder encoder;
    private final RestTemplate restTemplate;

    @Value("${secretKey}")
    private String secretKey;

    /**
     * API 설정 목록
     *
     * @return
     */
    public Map<String, String> getApiConfig() {
        String url = utils.adminUrl("/api/config/apikeys");
        String token = encoder.encode(secretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<JSONData> response = restTemplate.exchange(url, HttpMethod.GET, request, JSONData.class);

        System.out.println(response);
        return null;
    }
}
