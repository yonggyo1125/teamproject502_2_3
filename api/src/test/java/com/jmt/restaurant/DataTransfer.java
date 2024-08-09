package com.jmt.restaurant;

import com.jmt.restaurant.services.DataTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
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
}
