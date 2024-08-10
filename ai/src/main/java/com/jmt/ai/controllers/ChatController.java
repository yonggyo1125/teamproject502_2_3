package com.jmt.ai.controllers;

import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final OpenAiChatModel openAiChatModel;
    //private final VertexAiGeminiChatModel geminiChatModel;

    @RequestMapping("/")
    public JSONData chat(@RequestParam(name="message", required = false) String message) {
        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("메세지를 입력하세요.");
        }

       String openAiResponse = openAiChatModel.call(message);

        return new JSONData(openAiResponse);
    }
}
