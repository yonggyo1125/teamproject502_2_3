package com.jmt.member.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    public String createToken() {

        return null;
    }

    public Authentication getAuthentication(String token) {
        return null;
    }

    public void validateToken(String token) {

    }
}
