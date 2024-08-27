package com.jmt.restaurant;

import com.jmt.global.ListData;
import com.jmt.restaurant.controllers.RestaurantSearch;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.DataTransferService;
import com.jmt.restaurant.services.RestaurantImageService;
import com.jmt.restaurant.services.RestaurantInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@ActiveProfiles("test")
public class DataTransfer {

    @Autowired
    private DataTransferService service;

    @Autowired
    private RestaurantImageService imageService;

    @Autowired
    private RestaurantInfoService infoService;

    @Test
    @DisplayName("식당 기본 정보")
    void update1() {
        for (int i = 1; i < 10; i++) {
            service.update1(i);
        }
    }

    @Test
    @DisplayName("식당 이미지 정보")
    void update2() {
        for (int i = 1; i < 10; i++) {
            service.update2(i);
        }
    }
    
    @Test
    @DisplayName("메뉴 기본 정보")
    void update3() {
        for (int i = 1; i<= 100; i++) {
            service.update3(i);
        }
    }

    @Test
    @DisplayName("메뉴 이미지 정보")
    void update4() {
        service.update4(1);
        service.update4(2);
    }

    @Test
    @DisplayName("이미지가 없는 식당 이미지 업데이트")
    void update5() {
        for (int i = 1; i <= 10; i++) {
            RestaurantSearch search = new RestaurantSearch();
            search.setPage(i);
            search.setLimit(5000);
            ListData<Restaurant> data = infoService.getList(search);
            List<Restaurant> items = data.getItems();
            if (items == null || items.isEmpty()) continue;

            items.forEach(item -> imageService.update(item.getRstrId()));
        }
    }
}
