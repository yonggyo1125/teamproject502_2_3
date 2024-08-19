package com.jmt.reservation.services;

import com.jmt.payment.constants.PayMethod;
import com.jmt.payment.controllers.PayConfirmResult;
import com.jmt.payment.services.PaymentConfig;
import com.jmt.payment.services.PaymentConfigService;
import com.jmt.reservation.constants.ReservationStatus;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationPayService {

    private final ReservationInfoService reservationInfoService;
    private final PaymentConfigService paymentConfigService;
    private final ReservationStatusService reservationStatusService;
    private final ReservationRepository reservationRepository;

    public PaymentConfig getConfig(Long orderNo) {
        Reservation reservation = reservationInfoService.get(orderNo);
        PaymentConfig config = paymentConfigService.get(orderNo, reservation.getTotalPayPrice());

        String goodName = String.format("%s님의 %s 예약 결제", reservation.getName(), reservation.getRName());

        config.setGoodname(goodName);
        config.setBuyertel(reservation.getMobile());
        config.setBuyeremail(reservation.getEmail());
        config.setBuyername(reservation.getName());

        return config;
    }

    public Reservation update(PayConfirmResult result) {
        Long orderNo = result.getOrderNo();
        PayMethod method = result.getPayMethod();
        ReservationStatus status = method.equals(PayMethod.VBank) ? ReservationStatus.APPLY : ReservationStatus.CONFIRM;

        // 결제 정보 업데이트
        Reservation reservation = reservationInfoService.get(orderNo);
        reservation.setPayLog(result.getPayLog());
        reservation.setPayTid(result.getTid());
        reservation.setPayBankName(result.getBankName());
        reservation.setPayBankAccount(result.getBankAccount());
        reservationRepository.saveAndFlush(reservation);

        // 예약 상태 변경
        reservationStatusService.change(orderNo, status);

        return reservation;
    }
}
