package com.joyfarm.farmstival.global;

import com.joyfarm.farmstival.global.exceptions.UnAuthorizedException;
import com.joyfarm.farmstival.member.entities.JwtToken;
import com.joyfarm.farmstival.member.repositories.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils { // 빈의 이름 - utils

    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final DiscoveryClient discoveryClient;
    private final HttpSession session;
    private final JwtTokenRepository jwtTokenRepository;

    public HttpHeaders getCommonHeaders(String method) {

        JwtToken jwtToken =  jwtTokenRepository.findById(session.getId()).orElseThrow(UnAuthorizedException::new);
        /*
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDFAdGVzdC5vcmciLCJleHAiOjE3MjQ2NjQ5NjN9.5OfEnHQhoh7FL6ZTNUjPvRsf5UbaZl4d1W9eO0NfQ_FM1rWq-MJuCx8KWOmilwIhzDeDkTGvobHcTj9aivlbKw");
        */
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.getToken());

        if (!List.of("GET", "DELETE").contains(method)) { // GET, DELETE 이외 방식은 모두 Body 데이터 있다.
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        return headers;
    }

    public String url(String url){
        return url(url,"admin-service");
    }

    public String url(String url, String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        try{
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
            // 각 서버에서 지원하는 정적 자원의 경로는 게이트웨이 쪽에서 접근하는 것이 바람직 하지 않다!
        }catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    public String redirectUrl(String url) {
        String _fromGateway = Objects.requireNonNullElse(request.getHeader("from-gateway"), "false");
        String gatewayHost = Objects.requireNonNullElse(request.getHeader("gateway-host"), "");
        boolean fromGateway = _fromGateway.equals("true");

        return fromGateway ? request.getScheme() + "://" + gatewayHost + "/admin" + url : request.getContextPath() + url;
    }

    public Map<String, List<String>> getErrorMessages(Errors errors){ //JSON 받을 때는 에러를 직접 가공
        // FieldErrors


        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes())));

        // GlobalErrors
        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

        if (!gMessages.isEmpty()) {
            messages.put("global", gMessages);
        }
        return messages;
    }


    public List<String> getCodeMessages(String[] codes) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        return ms.getMessage(c, null, request.getLocale());
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .toList();

        ms.setUseCodeAsDefaultMessage(true);
        return messages;
    }

    public String getMessage(String code) {
        List<String> messages = getCodeMessages(new String[] {code});

        return messages.isEmpty() ? code : messages.get(0);
    }

    /**
     * 요청 데이터 단일 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 요청 데이터 복수개 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }
}
