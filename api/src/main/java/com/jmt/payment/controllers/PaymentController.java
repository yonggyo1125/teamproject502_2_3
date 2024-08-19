package com.jmt.payment.controllers;

import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.payment.services.PaymentProcessService;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.services.ReservationPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProcessService processService;
    private final ReservationPayService payService;
    private final Utils utils;

    @PostMapping("/process")
    public JSONData process(PayAuthResult result) {

        JSONData jsonData = new JSONData();
        PayConfirmResult confirmResult = processService.process(result);
        if (confirmResult == null) { // 결제 실패시에는 주문 실패 페이지로 이동

            jsonData.setSuccess(false);
            return jsonData;
        }

        Reservation reservation = payService.update(confirmResult);
        if (reservation == null) {
            jsonData.setSuccess(false);
            return jsonData;
        }

        return new JSONData(reservation);
    }
}
