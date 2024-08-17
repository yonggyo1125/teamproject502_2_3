package com.jmt.ai.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiPromptServiceTest {
    @Autowired
    private AiPromptService service;

    @Test
    void test1() {
        service.prompt("오늘 점심 메뉴 추천, 한국어로 답해줘");
    }
}
