package com.jmt.restaurant.services;

import com.jmt.global.rests.gov.api.ApiResult;
import com.jmt.restaurant.entities.FoodMenu;
import com.jmt.restaurant.entities.FoodMenuImage;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.entities.RestaurantImage;
import com.jmt.restaurant.repositories.FoodMenuImageRepository;
import com.jmt.restaurant.repositories.FoodMenuRepository;
import com.jmt.restaurant.repositories.RestaurantImageRepository;
import com.jmt.restaurant.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Lazy
@Service
@Transactional
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
    public void update1(int pageNo) {
        pageNo = Math.max(pageNo, 1);

        String url = String.format("https://seoul.openapi.redtable.global/api/rstr?serviceKey=%s&pageNo=%d", serviceKey, pageNo);

        ResponseEntity<ApiResult> response = restTemplate.getForEntity(URI.create(url), ApiResult.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        ApiResult result = response.getBody();
        if (!result.getHeader().get("resultCode").equals("00")) {
            return;
        }

        List<Map<String, String>> tmp = result.getBody();

        String url2 = String.format("https://seoul.openapi.redtable.global/api/rstr/oprt?serviceKey=%s&pageNo=%d", serviceKey, pageNo);
        ResponseEntity<ApiResult> result2 = restTemplate.getForEntity(URI.create(url2), ApiResult.class);

        List<Map<String, String>> tmp2 = result2.getBody().getBody();

        List<Restaurant> items = tmp.stream()
                .map(d -> {
                        Map<String, String> extra = getExtra(tmp2, d.get("RSTR_ID"), "RSTR");

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
                        rest.setPrkgPosYn(extra.get("PRKG_POS_YN").equals("Y"));
                        rest.setWifiOfrYn(extra.get("WIFI_OFR_YN").equals("Y"));
                        rest.setDcrnYn(extra.get("DCRN_YN").equals("Y"));
                        rest.setPetEntrnPosblYn(extra.get("PET_ENTRN_POSBL_YN").equals("Y"));
                        rest.setFgggMenuOfrYn(extra.get("FGGG_MENU_OFR_YN").equals("Y"));
                        rest.setTlromInfoCn(extra.get("TLROM_INFO_CN"));
                        rest.setRestdyInfoCn(extra.get("RESTDY_INFO_CN"));
                        rest.setBsnsTmCn(extra.get("BSNS_TM_CN"));
                        rest.setHmdlvSaleYn(extra.get("HMDLV_SALE_YN").equals("Y"));
                        rest.setDsbrCvntlYn(extra.get("DSBR_CVNTL_YN").equals("Y"));
                        rest.setDelvSrvicYn(extra.get("DELV_SRVIC_YN").equals("Y"));
                        rest.setRsrvMthdNm(extra.get("RSRV_MTHD_NM"));
                        rest.setOnlineRsrvInfoCn(extra.get("ONLINE_RSRV_INFO_CN"));
                        rest.setHmpgUrl(extra.get("HMPG_URL"));
                        rest.setKioskYn(extra.get("KIOSK_YN").equals("Y"));
                        rest.setMbPmamtYn(extra.get("MB_PMAMT_YN").equals("Y"));
                        rest.setSmorderYn(extra.get("SMORDER_YN").equals("Y"));
                        rest.setReprsntMenuNm(extra.get("REPRSNT_MENU_NM"));

                        return rest;
                }).toList();

        if (items == null || items.isEmpty()) {
            return;
        }

        restaurantRepository.saveAllAndFlush(items);
    }



    /**
     * 식당 이미지 업데이트
     *
     */
    public void update2(int pageNo) {
        pageNo = Math.max(pageNo, 1);

        String url = String.format("https://seoul.openapi.redtable.global/api/rstr/img?serviceKey=%s&pageNo=%d", serviceKey, pageNo);

        ResponseEntity<ApiResult> response = restTemplate.getForEntity(URI.create(url), ApiResult.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        ApiResult result = response.getBody();
        if (!result.getHeader().get("resultCode").equals("00")) {
            return;
        }

        List<Map<String, String>> tmp = result.getBody();
        if (tmp == null || tmp.isEmpty()) return;

        List<RestaurantImage> items = tmp.stream()
                .map(d -> {
                    Restaurant restaurant = restaurantRepository.findById(Long.valueOf(d.get("RSTR_ID"))).orElse(null);

                    return RestaurantImage.builder()
                            .restaurant(restaurant)
                            .rstrImgUrl(d.get("RSTR_IMG_URL"))
                            .build();
                }).toList();

        if (items == null || items.isEmpty()) return;

        restaurantImageRepository.saveAllAndFlush(items);
    }

    /**
     * 메뉴 정보 업데이트
     *
     */
    public void update3(int pageNo) {
        pageNo = Math.max(pageNo, 1);

        String url = String.format("https://seoul.openapi.redtable.global/api/menu/korean?serviceKey=%s&pageNo=%d", serviceKey, pageNo);

        ResponseEntity<ApiResult> response = restTemplate.getForEntity(URI.create(url), ApiResult.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        ApiResult result = response.getBody();
        if (!result.getHeader().get("resultCode").equals("00")) {
            return;
        }

        List<Map<String, String>> tmp = result.getBody();
        if (tmp == null || tmp.isEmpty()) return;

        String url2 = String.format("https://seoul.openapi.redtable.global/api/menu-dscrn/korean?serviceKey=%s&pageNo=%d", serviceKey, pageNo);
        ResponseEntity<ApiResult> result2 = restTemplate.getForEntity(URI.create(url2), ApiResult.class);

        List<Map<String, String>> tmp2 = result2.getBody().getBody();

        List<FoodMenu> items = tmp.stream()
                .map(d -> {
                    Restaurant restaurant = restaurantRepository.findById(Long.valueOf(d.get("RSTR_ID"))).orElse(null);

                    FoodMenu food =  FoodMenu.builder()
                            .restaurant(restaurant)
                            .menuId(Long.valueOf(d.get("MENU_ID")))
                            .menuNm(d.get("MENU_NM"))
                            .menuPrice(Integer.valueOf(d.get("MENU_PRICE")))
                            .spcltMenuYn(d.get("SPCLT_MENU_YN").equals("Y"))
                            .spcltMenuOgnUrl(d.get("SPCLT_MENU_OGN_URL"))
                            .spcltMenuNm(d.get("SPCLT_MENU_NM"))
                            .build();

                    Map<String, String> extra = getExtra(tmp2, d.get("MENU_ID"), "MENU");
                    if (extra != null) {
                        food.setMenuDscrn(extra.get("MENU_DSCRN"));
                        food.setMenuCtgryLclasNm(extra.get("MENU_CTGRY_LCLAS_NM"));
                        food.setMenuCtgrySclasNm(extra.get("MENU_CTGRY_SCLAS_NM"));
                    }

                    return food;
                }).toList();

        if (items == null || items.isEmpty()) return;

        foodMenuRepository.saveAllAndFlush(items);
    }

    /**
     * 메뉴 이미지 업데이트
     *
     */
    public void update4(int pageNo) {
        pageNo = Math.max(pageNo, 1);

        String url = String.format("https://seoul.openapi.redtable.global/api/food/img?serviceKey=%s&pageNo=%d", serviceKey, pageNo);

        ResponseEntity<ApiResult> response = restTemplate.getForEntity(URI.create(url), ApiResult.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        ApiResult result = response.getBody();
        if (!result.getHeader().get("resultCode").equals("00")) {
            return;
        }

        List<Map<String, String>> tmp = result.getBody();
        if (tmp == null || tmp.isEmpty()) return;

        List<FoodMenuImage> items = tmp.stream()
                .map(d -> {
                    FoodMenu foodMenu = foodMenuRepository.findById(Long.valueOf(d.get("MENU_ID"))).orElse(null);

                    return FoodMenuImage.builder()
                            .foodMenu(foodMenu)
                            .foodImgUrl(d.get("FOOD_IMG_URL"))
                            .build();
                }).toList();

            if (items == null || items.isEmpty()) return;
            foodMenuImageRepository.saveAllAndFlush(items);
    }

    private Map<String, String> getExtra(List<Map<String, String>> items, String id, String mode) {
        if (items == null || items.isEmpty()) return null;

        return items.stream()
                .filter(d -> d.get(mode + "_ID").equals(id))
                .findFirst().orElse(null);
    }
}
