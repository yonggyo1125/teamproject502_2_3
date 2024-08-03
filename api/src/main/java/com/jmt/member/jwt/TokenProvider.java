package com.jmt.member.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class TokenProvider {

    private final JwtProperties properties;
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
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) { // 로그인 성공시 -> JWT 토큰 발급
            long now = (new Date()).getTime();
            Date validity = new Date(now + properties.getValidSeconds() * 1000);

            return Jwts.builder()
                    .setSubject(authentication.getName()) // 사용자 email
                    .signWith(getKey(), SignatureAlgorithm.HS512) // HMAC + SHA512
                    .expiration(validity)
                    .compact();
        }

        return null;
    }

    public Authentication getAuthentication(String token) {
        return null;
    }

    public void validateToken(String token) {

    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
