package com.jmt.activity.services;

import com.jmt.activity.constants.AM_PM;
import com.jmt.activity.constants.Status;
import com.jmt.activity.controllers.RequestReservation;
import com.jmt.activity.entities.Activity;
import com.jmt.activity.entities.Reservation;
import com.jmt.activity.exceptions.ActivityNotFoundException;
import com.jmt.activity.repositories.ActivityRepository;
import com.jmt.activity.repositories.ReservationRepository;
import com.jmt.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ReservationApplyService {
    private final ReservationRepository reservationRepository;
    private final ActivityRepository activityRepository;
    private final ReservationStatusService statusService;
    private final MemberUtil memberUtil;

    public Reservation apply(RequestReservation form) {
        Long activitySeq = form.getActivitySeq();
        Activity activity = activityRepository.findById(activitySeq).orElseThrow(ActivityNotFoundException::new);

        String mobile = form.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
        }

        Reservation reservation = Reservation.builder()
                .activity(activity)
                .email(form.getEmail())
                .name(form.getName())
                .mobile(mobile)
                .member(memberUtil.getMember())
                .activityName(activity.getActivityName())
                .townName(activity.getTownName())
                .doroAddress(activity.getDoroAddress())
                .ownerName(activity.getOwnerName())
                .ownerTel(activity.getOwnerTel())
                .rDate(form.getRDate())
                .ampm(AM_PM.valueOf(form.getAmpm()))
                .persons(Math.max(form.getPersons(), 1))
                .build();

        reservationRepository.saveAndFlush(reservation);


        // 예약 접수 상태로 변경
        statusService.change(reservation.getSeq(), Status.APPLY);

        return reservation;
    }
}
