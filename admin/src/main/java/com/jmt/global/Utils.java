package com.jmt.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiscoveryClient discoveryClient;

    private final HttpServletRequest request;

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    /*
    public String redirectUrl(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");
        String fromGateway = request.getHeader("from-gateway");
        if (StringUtils.hasText("fromGateway") && fromGateway.equals("true")) {
            String host = request.getHeader("gateway-port");
            String protocol = request.isSecure() ? "https://" : "http://";
             url = protocol + host + "/admin" + url;
        }

        return "redirect:" + url;

    }

     */

}
