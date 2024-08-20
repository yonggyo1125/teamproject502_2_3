package com.jmt.payment.services;

import com.jmt.global.SHA256;
import com.jmt.global.services.ConfigInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentConfigService {
    private final ConfigInfoService infoService;

    public PaymentConfig get(Long oid, Integer price) {
        try {
            Map<String, String> _data = infoService.getConfig("payment");
            PaymentConfig config = new PaymentConfig();
            config.setSignKey(_data.get("signKey"));
            config.setMid(_data.get("mid"));

            List<String> payMethods = Arrays.stream(_data.getOrDefault("payMethods", "")
                    .split(",")).toList();
            config.setPayMethods(payMethods);

            if (oid == null || price == null) {
                return config;
            }

            long timestamp = new Date().getTime();
             String signKey = config.getSignKey();


            // signature S
            String data = String.format("oid=%d&price=%d&timestamp=%d", oid, price, timestamp);
            String signature = SHA256.encrypt(data);
            config.setSignature(signature);
            // signature E

            // verification S
            data = String.format("oid=%d&price=%d&signKey=%s&timestamp=%d", oid, price, signKey, timestamp);
            String verification = SHA256.encrypt(data);
            config.setVerification(verification);
            // verification E

            // mKey
            String mKey = SHA256.encrypt(signKey);
            config.setMKey(mKey);

            config.setTimestamp(timestamp);
            config.setOid(oid);
            config.setPrice(price);

            return config;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public PaymentConfig get() {
        return get(null, null);
    }
}
