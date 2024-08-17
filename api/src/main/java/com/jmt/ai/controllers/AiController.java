package com.jmt.ai.controllers;

import com.jmt.ai.services.AiPromptService;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiPromptService service;

    @GetMapping
    public JSONData index(@RequestParam("message") String message) {
        String response = service.prompt(message);

        JSONData jsonData = new JSONData(response);
        jsonData.setSuccess(response != null);

        return jsonData;
    }
}
