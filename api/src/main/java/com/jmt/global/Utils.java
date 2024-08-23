package com.jmt.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils { // 빈의 이름 - utils

    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public Map<String, List<String>> getErrorMessages(Errors errors) {
        // FieldErrors

        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes()), (p1, p2) -> p1));

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

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("api-service");

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    public String adminUrl(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");
        return String.format("%s%s", instances.get(0).getUri().toString(), url);
    }

    /**
     * 광역시 짧은 명칭 반환
     *
     * @param sido
     * @return
     */
    public String getShortSido(String sido) {
        String sido2 = "";
        if (sido != null && StringUtils.hasText(sido.trim())) {

            if (sido.equals("서울특별시")) sido2 = "서울";
            else if (sido.equals("인천광역시")) sido2 = "인천";
            else if (sido.equals("경기도")) sido2 = "경기";
            else if (sido.equals("강원도") || sido.equals("강원특별자치도")) sido2 = "강원";
            else if (sido.equals("충청북도")) sido2 = "충북";
            else if (sido.equals("충청남도")) sido2 = "충남";
            else if (sido.equals("경상북도")) sido2 = "경북";
            else if (sido.equals("경상남도")) sido2 = "경남";
            else if (sido.equals("전라북도")) sido2 = "전북";
            else if (sido.equals("전라남도")) sido2 = "전남";
            else if (sido.equals("대전광역시")) sido2 = "대전";
            else if (sido.equals("세종특별자치시")) sido2 = "세종";
            else if (sido.equals("제주특별자치도")) sido2 = "제주";
        }
        return StringUtils.hasText(sido2) ? sido2 : sido;
    }

    /**
     * 광역시 긴 명칭 반환
     *
     * @param sido
     * @return
     */
    public String getLongSido(String sido) {
        String sido2 = "";
        if (sido != null && StringUtils.hasText(sido.trim())) {

            if (sido.equals("서울")) sido2 = "서울특별시";
            else if (sido.equals("인천")) sido2 = "인천광역시";
            else if (sido.equals("경기")) sido2 = "경기도";
            else if (sido.equals("강원")) sido2 = "강원";
            else if (sido.equals("충북")) sido2 = "충청북도";
            else if (sido.equals("충남")) sido2 = "충청남도";
            else if (sido.equals("경북")) sido2 = "경상북도";
            else if (sido.equals("경남")) sido2 = "경상남도";
            else if (sido.equals("전북")) sido2 = "전라북도";
            else if (sido.equals("전남")) sido2 = "전라남도";
            else if (sido.equals("대전")) sido2 = "대전광역시";
            else if (sido.equals("세종")) sido2 = "세종특별자치시";
            else if (sido.equals("제주")) sido2 = "제주특별자치도";
        }
        return StringUtils.hasText(sido2) ? sido2 : sido;
    }
}