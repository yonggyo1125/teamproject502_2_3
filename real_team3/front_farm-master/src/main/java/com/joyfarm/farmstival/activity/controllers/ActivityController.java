package com.joyfarm.farmstival.activity.controllers;

import com.joyfarm.farmstival.activity.entities.Activity;
import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.services.ActivityInfoService;
import com.joyfarm.farmstival.activity.services.ReservationApplyService;
import com.joyfarm.farmstival.activity.services.ReservationInfoService;
import com.joyfarm.farmstival.activity.validators.ReservationValidator;
import com.joyfarm.farmstival.global.CommonSearch;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.BadRequestException;
import com.joyfarm.farmstival.global.rests.JSONData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ActivityController {

    private final ReservationInfoService reservationInfoService;
    private final ActivityInfoService activityInfoService;
    private final ReservationValidator validator;
    private final ReservationApplyService applyService;
    private final Utils utils;

    /**
     * 목록 조회
     * @param search
     * @return
     */
    @GetMapping("/list")
    public JSONData list(@ModelAttribute ActivitySearch search) {
        ListData<Activity> data = activityInfoService.getList(search);

        return new JSONData(data);
    }

    /**
     * 상세 조회
     * @param seq
     * @return
     */
    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {

        Activity data = activityInfoService.get(seq);

        return new JSONData(data);
    }

    /**
     * 예약 접수
     * @return
     */
    @PostMapping("/apply")
    public ResponseEntity<JSONData> apply(@RequestBody @Valid RequestReservation form, Errors errors) {
        validator.validate(form, errors); //예약 검증

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        Reservation reservation = applyService.apply(form);

        HttpStatus status = HttpStatus.CREATED;
        JSONData jsonData = new JSONData(reservation);
        jsonData.setStatus(status);

        return ResponseEntity.status(status).body(jsonData);
    }


    //예약 정보 조회
    @GetMapping("/complete/{seq}")
    public JSONData rsvInfo(@PathVariable("seq") Long seq) {
        Reservation reservation = reservationInfoService.get(seq);

        return new JSONData(reservation);
    }
    
    @GetMapping("/wish")
    @PreAuthorize("isAuthenticated()") //인증된(로그인한) 회원만 접근 가능
    public JSONData wishList(@ModelAttribute CommonSearch search) {
        //없을 수도 있기 때문에 @ModelAttribute 추가
        ListData<Activity> data = activityInfoService.getWishList(search);

        return new JSONData(data);
    }
}