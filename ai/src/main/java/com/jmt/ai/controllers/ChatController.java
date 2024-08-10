package com.jmt.ai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final OpenAiChatModel openAiChatModel;
    //private final VertexAiGeminiChatModel geminiChatModel;

    @RequestMapping("/")
    public void chat(@RequestParam("message") String message) {
       String openAiResponse = openAiChatModel.call(message);

        /*
        String geminiResponse = geminiChatModel.call(message);
        //System.out.println(openAiResponse);
        System.out.println(geminiResponse);
        */
    }
}
