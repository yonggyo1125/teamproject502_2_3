package com.joyfarm.farmstival.global.rests;

import com.joyfarm.farmstival.member.entities.JwtToken;
import com.joyfarm.farmstival.member.repositories.JwtTokenRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ApiRequest {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository repository;
    private final HttpSession session;

    public <R> ResponseEntity<R> request(String url, HttpMethod method, String json, Class<R> clazz) {

        HttpHeaders headers = new HttpHeaders();
        JwtToken token = repository.findById(session.getId()).orElse(null);
        System.out.println("token: " + token);
        if (token != null) {
            headers.setBearerAuth(token.getToken());
        }

        HttpEntity<String> entity = null;
        if (StringUtils.hasText(json)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<>(json, headers);
        } else {
            entity = new HttpEntity<>(headers);
        }



        ResponseEntity<R> response = restTemplate.exchange(URI.create(url), method, entity, clazz);

        return response;
    }

    public <R> ResponseEntity<R> request(String url, Class<R> clazz) {

        return request(url, HttpMethod.GET, null, clazz);
    }
}
