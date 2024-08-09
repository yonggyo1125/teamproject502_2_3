package com.jmt.restaurant;

import com.jmt.restaurant.services.DataTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ActiveProfiles("test")
public class DataTransfer {

    @Autowired
    private DataTransferService service;

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
}
