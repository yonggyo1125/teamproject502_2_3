package com.jmt.ai.services;

import com.jmt.ai.controllers.RequestMessage;
import com.jmt.global.services.ConfigInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiPromptService {
    private final ConfigInfoService infoService;

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


    }
}
