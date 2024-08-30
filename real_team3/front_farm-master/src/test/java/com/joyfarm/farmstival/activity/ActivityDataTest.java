package com.joyfarm.farmstival.activity;

import com.joyfarm.farmstival.activity.controllers.RequestReservation;
import com.joyfarm.farmstival.activity.entities.Activity;
import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.services.ActivityInfoService;
import com.joyfarm.farmstival.activity.services.ReservationApplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ActivityDataTest {

    @Autowired
    private ActivityInfoService infoService;

    @Autowired
    private ReservationApplyService applyService;

    private Activity activity;

    @Test
    @DisplayName("체험활동 데이터 seq로 상세 조회 테스트")
    void test01() {

        Activity item = infoService.get(1234L);
        System.out.println(item);
    }

    @Test
    @DisplayName("예약 신청 테스트")
    void test02() {

        RequestReservation form = new RequestReservation();
        form.setActivitySeq(1234L);
        form.setEmail("test01@test.org");
        form.setMobile("01011112222");
        form.setName("user01");
        form.setAmpm("AM");
        form.setRDate(LocalDate.of(2024, 8, 29));
        form.setPersons(1);
        System.out.println(form);


        Reservation reserve = applyService.apply(form);
        System.out.println(reserve);

    }
}
