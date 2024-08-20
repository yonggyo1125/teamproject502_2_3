package com.jmt.reservation.services;

import com.jmt.member.MemberUtil;
import com.jmt.payment.constants.PayMethod;
import com.jmt.reservation.constants.ReservationStatus;
import com.jmt.reservation.controllers.RequestReservation;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.repositories.ReservationRepository;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final RestaurantInfoService infoService;
    private final ReservationRepository repository;
    private final MemberUtil memberUtil;

    public Reservation save(RequestReservation form) { // 예약 접수

        long orderNo = System.currentTimeMillis();
        String payMethod = form.getPayMethod();
        String mobile = form.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
        }

        Long rstrId = form.getRstrId();
        Restaurant restaurant = infoService.get(rstrId);

        Reservation item = Reservation.builder()
                .orderNo(orderNo)
                .email(form.getEmail())
                .name(form.getName())
                .mobile(mobile)
                .payMethod(payMethod == null? null : PayMethod.valueOf(payMethod))
                .persons(Math.max(form.getPersons(), 1))
                .price(1000)
                .restaurant(restaurant)
                .member(memberUtil.getMember())
                .rName(restaurant.getRstrNm())
                .rAddress(restaurant.getRstrRdnmAdr())
                .rTel(restaurant.getRstrTelNo())
                .rDateTime(LocalDateTime.of(form.getRDate(), form.getRTime()))
                .status(ReservationStatus.START)
                .build();

        return save(item);

    }

    public Reservation save(Reservation item) {

        repository.saveAndFlush(item);

        return item;
    }
}
