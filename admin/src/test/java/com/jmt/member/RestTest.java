package com.jmt.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.rests.JSONData;
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


        String serviceUri = String.format("%s/account/token", instances.get(0).getUri().toString());
        RequestLogin form = new RequestLogin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestLogin> request = new HttpEntity<>(form, headers);

        ResponseEntity<JSONData> response = restTemplate.exchange(serviceUri, HttpMethod.POST, request, JSONData.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONData data = response.getBody();
            String token = (String)data.getData();
            System.out.println(token);
            headers = new HttpHeaders();
            headers.add("Authentication", "Bearer " + token);
            HttpEntity<Object> request2 = new HttpEntity<>(headers);

            String serviceUri2 = String.format("%s/account/info", instances.get(0).getUri().toString());
            System.out.println(serviceUri2);
            ResponseEntity<JSONData> data2 = restTemplate.exchange(serviceUri2, HttpMethod.GET, request2, JSONData.class);

            System.out.println(data2);
        }
    }
}
