package com.jmt.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.member.controllers.RequestLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RestTest {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test1() throws Exception {
        List<ServiceInstance> instances = discoveryClient.getInstances("api-service");


        String serviceUri = String.format("%s/api/v1/account/token", instances.get(0).getUri().toString());
        System.out.println(serviceUri);
        RequestLogin form = new RequestLogin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        String params = om.writeValueAsString(form);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(serviceUri, HttpMethod.POST, request, String.class);

        System.out.println(response);
    }
}
