package com.jmt.ai.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.ai.controllers.RequestMessage;
import com.jmt.global.services.ConfigInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class AiPromptService {
    private final ConfigInfoService infoService;
    private final ObjectMapper om;
    private final RestTemplate restTemplate;

    public String prompt(String message) {
        Map<String, String> config = infoService.getApiConfig();
        if (config == null || !StringUtils.hasText(config.get("huggingfaceAccessToken"))) {
            return null;
        }

        String token = config.get("huggingfaceAccessToken").trim();

        String url = "https://api-inference.huggingface.co/models/mistralai/Mistral-Nemo-Instruct-2407/v1/chat/completions";

        Map<String, String> data = new HashMap<>();
        data.put("role", "user");
        data.put("content", message);
        RequestMessage params = new RequestMessage(List.of(data));
        try {
            String json = om.writeValueAsString(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(URI.create(url), request, String.class);

            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
