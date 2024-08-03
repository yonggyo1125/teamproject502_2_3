package com.jmt.member.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TokenProviderTest {
    @Autowired
    private TokenProvider provider;

    @Test
    @DisplayName("토큰 발급 테스트")
    @WithMockUser(username="user01@test.org", password="_aA123456", authorities = "USER")
    void createTokenTest() {
        String token = provider.createToken("user01@test.org", "_aA123456");
        System.out.println(token);
    }
}
