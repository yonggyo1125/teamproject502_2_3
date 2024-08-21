package com.jmt.restaurant;

import com.jmt.reservation.services.RestaurantImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateImageTest {
    @Autowired
    private RestaurantImageService imageService;

    @Test
    void test1() {
        //String imageUrl = imageService.collect(1943L);
        imageService.update(1943L);
    }
}
