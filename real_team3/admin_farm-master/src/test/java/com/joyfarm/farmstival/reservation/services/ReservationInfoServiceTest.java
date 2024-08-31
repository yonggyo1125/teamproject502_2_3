package com.joyfarm.farmstival.reservation.services;

import com.joyfarm.farmstival.reservation.controllers.ReservationSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationInfoServiceTest {
    @Autowired
    private ReservationInfoService infoService;

    @Test
    void test1() {
        ReservationSearch search = new ReservationSearch();
        infoService.getList(search);
    }
}
