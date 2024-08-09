package com.jmt.restaurant.services;

import com.jmt.global.rests.gov.api.ApiResult;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.repositories.FoodMenuImageRepository;
import com.jmt.restaurant.repositories.FoodMenuRepository;
import com.jmt.restaurant.repositories.RestaurantImageRepository;
import com.jmt.restaurant.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Lazy
@Service
@RequiredArgsConstructor
public class DataTransferService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenuImageRepository foodMenuImageRepository;

    private final RestTemplate restTemplate;
    private String serviceKey = "BOYbPA7XwfKbTICZBI6Myilx082zrTanL2VbWfQxwHMa8WA1YwyN2PkhdIeJZkmL";

    /**
     * 식당 기본 정보
     *
     */
    public void update1() {
        String url = String.format("https://seoul.openapi.redtable.global/api/rstr?serviceKey=%s", serviceKey);

        ResponseEntity<ApiResult> response = restTemplate.getForEntity(URI.create(url), ApiResult.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        ApiResult result = response.getBody();
        if (!result.getHeader().get("resultCode").equals("00")) {
            return;
        }

        List<Map<String, String>> tmp = result.getBody();

        List<Restaurant> items = tmp.stream()
                .map(d -> Restaurant.builder()
                        .rstrId(Long.valueOf(d.get("RSTR_ID")))
                        .rstrNm(d.get("RSTR_NM"))
                        .rstrRdnmAdr(d.get("RSTR_RDNMADR"))
                        .rstrLnnoAdres(d.get("RSTR_LNNO_ADRES"))
                        .rstrLa(Double.valueOf(d.get("RSTR_LA")))
                        .rstrLo(Double.valueOf(d.get("RSTR_LO")))
                        .rstrTelNo(d.get("RSTR_TELNO"))
                        .dbsnsStatmBzcndNm(d.get("BSNS_STATM_BZCND_NM"))
                        .bsnsLcncNm(d.get("BSNS_LCNC_NM"))
                        .rstrIntrcnCont(d.get("RSTR_INTRCN_CONT"))
                        .build()).toList();

    }
}
