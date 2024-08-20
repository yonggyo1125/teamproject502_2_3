package com.jmt.payment.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.SHA256;
import com.jmt.global.Utils;
import com.jmt.payment.constants.PayMethod;
import com.jmt.payment.controllers.PayAuthResult;
import com.jmt.payment.controllers.PayConfirmResult;
import com.jmt.payment.exceptions.PaymentAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentProcessService {
    private final PaymentConfigService configService;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;

    /**
     * 인증 데이터로 결제 승인 처리
     * @param result
     */
    public PayConfirmResult process(PayAuthResult result) {

        String mid = result.getMid();
        String authToken = result.getAuthToken();
        String authUrl = result.getAuthUrl();
        String netCancelUrl = result.getNetCancelUrl();

        PaymentConfig config = configService.get();
        String signKey = config.getSignKey();

        long timestamp = new Date().getTime();

        // 인증 실패, 승인 실패시 이동할 주소
        if (!result.getResultCode().equals("0000")) { // 인증 실패시

            // 결제 승인 취초

            throw new PaymentAuthException(result.getResultMsg());
        }



        // 요청 데이터

        String signature = "";
        String verification = "";
        try {
            signature = SHA256.encrypt(String.format("authToken=%s&timestamp=%d", authToken, timestamp));
            verification = SHA256.encrypt(String.format("authToken=%s&signKey=%s&timestamp=%d", authToken, signKey, timestamp));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("mid", mid);
        params.add("authToken", authToken);
        params.add("timestamp", String.valueOf(timestamp));
        params.add("signature", signature);
        params.add("verification", verification);
        params.add("charset", "UTF-8");
        params.add("format", "JSON");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URI.create(authUrl), request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                Map<String, String> resultMap = om.readValue(response.getBody(), new TypeReference<>() {});
                if (!resultMap.get("resultCode").equals("0000")) {
                    return null;
                }

                PayMethod payMethod = PayMethod.valueOf(resultMap.get("payMethod"));

                String payLog = resultMap.entrySet()
                        .stream()
                        .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue())).collect(Collectors.joining("\n"));

                return PayConfirmResult.builder()
                        .orderNo(Long.valueOf(resultMap.get("MOID")))
                        .tid("tid")
                        .payMethod(payMethod)
                        .bankAccount(resultMap.get("vactBankName")) // 가상계좌은행
                        .bankAccount(resultMap.get("VACT_Num")) // 가상계좌번호
                        .payLog(payLog)
                        .build();

            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } // endif

        return null;
    }
}
