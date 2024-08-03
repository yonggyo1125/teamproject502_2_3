package com.jmt.member.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {


    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * JWT 토큰 생성
     *
     * @param email
     * @param password
     * @return
     */
    public String createToken(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication =
        return null;
    }

    public Authentication getAuthentication(String token) {
        return null;
    }

    public void validateToken(String token) {

    }
}
