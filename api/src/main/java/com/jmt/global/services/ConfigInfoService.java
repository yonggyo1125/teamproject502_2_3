package com.jmt.global.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
    private final ObjectMapper om;

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
        if (response.getStatusCode().isSameCodeAs(HttpStatus.OK) && response.getBody().isSuccess()) {
            try {
                JSONData data = response.getBody();
                Map<String, String> config = om.readValue(om.writeValueAsString(data.getData()), new TypeReference<>() {
                });
                return config;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
