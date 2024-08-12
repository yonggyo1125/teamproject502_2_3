package com.jmt.member.social;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class KakaoLoginTest1 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private String key = "53d306cb8455067fd1831a77b1201c31";

    @Test
    @DisplayName("코드 발급 테스트")
    void test1() throws Exception {
        String client_id = "53d306cb8455067fd1831a77b1201c31";
        String redirect_uri = "http://localhost:3000/app/member/social";
        String response_type = "code";
        String state = "123";

        String url = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=%s", client_id, redirect_uri, response_type, state);

        mockMvc.perform(get(url))
                .andDo(print());

    }
}
