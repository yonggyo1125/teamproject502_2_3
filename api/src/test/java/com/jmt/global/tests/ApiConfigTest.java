package com.jmt.global.tests;

import com.jmt.global.services.ConfigInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiConfigTest {
    @Autowired
    private ConfigInfoService infoService;

    @Test
    void test1() {
        infoService.getApiConfig();
    }
}
