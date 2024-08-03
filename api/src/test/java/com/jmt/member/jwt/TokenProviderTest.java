package com.jmt.member.jwt;

import com.jmt.member.controllers.RequestJoin;
import com.jmt.member.services.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private MemberSaveService saveService;

    private RequestJoin form;

    @BeforeEach
    void init() {
        form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword(form.getPassword());
        form.setMobile("010-1000-1000");
        form.setUserName("사용자01");
        form.setAgree(true);
        saveService.save(form);
    }

    @Test
    @DisplayName("토큰 발급 테스트")
    @WithMockUser(username="user01@test.org", password="_aA123456", authorities = "USER")
    void createTokenTest() {
        String token = provider.createToken("user01@test.org", "_aA123456");
        System.out.println(token);
    }
}
