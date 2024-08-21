package com.jmt.restaurant;

import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantDataTest {
    @Autowired
    private RestaurantInfoService infoService;

    @Test
    void test1() {
        Long rstrId = 1943L;
        Restaurant item = infoService.get(rstrId);
        System.out.println(item);
    }
}
