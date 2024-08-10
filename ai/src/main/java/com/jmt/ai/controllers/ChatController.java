package com.jmt.ai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChatController {
    private final OpenAiChatModel openAiChatModel;
    private final VertexAiGeminiChatModel geminiChatModel;

    @GetMapping
    public void chat(@RequestParam("message") String message) {
        String openAiResponse = openAiChatModel.call(message);

        String geminiResponse = geminiChatModel.call(message);
        System.out.println(openAiResponse);
        System.out.println(geminiResponse);

    }
}
