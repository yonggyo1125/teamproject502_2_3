package com.joyfarm.farmstival.global.rests;

import com.joyfarm.farmstival.member.entities.JwtToken;
import com.joyfarm.farmstival.member.repositories.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ApiRequest {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository repository;

    public <T, R> ResponseEntity<R> request(String url, HttpMethod method, HttpEntity<T> entity, Class<R> clazz) {
        HttpHeaders headers = entity.getHeaders();
        JwtToken token = repository.findById("token").orElse(null);
        if (token != null) {
            headers.setBearerAuth(token.getToken());
        }

        ResponseEntity<R> response = restTemplate.exchange(URI.create(url), method, entity, clazz);

        return response;
    }
}
