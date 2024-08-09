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

        String url2 = String.format("https://seoul.openapi.redtable.global/api/rstr/oprt?serviceKey=%s", serviceKey);
        ResponseEntity<ApiResult> result2 = restTemplate.getForEntity(URI.create(url2), ApiResult.class);

        List<Map<String, String>> tmp2 = result2.getBody().getBody();

        List<Restaurant> items = tmp.stream()
                .map(d -> {
                        Map<String, String> extra = getExtra(tmp2, d.get("RSTR_ID"));

                        Restaurant rest =  Restaurant.builder()
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
                            .build();

                        if (extra == null) {
                            return rest;
                        }

                        rest.setAreaNm(extra.get("AREA_NM"));

                        if (extra.get("PRDL_SEAT_CNT") != null) rest.setPrdlSeatCnt(Integer.valueOf(extra.get("PRDL_SEAT_CNT")));
                        if (extra.get("SEAT_CNT") != null) rest.setSeatCnt(Integer.valueOf(extra.get("SEAT_CNT")));
                        rest.setPrkgPosYn(d.get("PRKG_POS_YN").equals("Y"));
                        rest.setWifiOfrYn(d.get("WIFI_OFR_YN").equals("Y"));
                        rest.setDcrnYn(d.get("DCRN_YN").equals("Y"));
                        rest.setPetEntrnPosblYn(d.get("PET_ENTRN_POSBL_YN").equals("Y"));
                        rest.setFgggMenuOfrYn(d.get("FGGG_MENU_OFR_YN").equals("Y"));
                        rest.setTlromInfoCn(d.get("TLROM_INFO_CN"));
                        rest.setRestdyInfoCn(d.get("RESTDY_INFO_CN"));
                        rest.setBsnsTmCn(d.get("BSNS_TM_CN"));
                        rest.setHmdlvSaleYn(d.get("HMDLV_SALE_YN").equals("Y"));

                        return rest;
                }).toList();

        //items.forEach(System.out::println);

    }

    private Map<String, String> getExtra(List<Map<String, String>> items, String rstrId) {
        if (items == null || items.isEmpty()) return null;

        return items.stream()
                .filter(d -> d.get("RSTR_ID").equals(rstrId))
                .findFirst().orElse(null);
    }
}
