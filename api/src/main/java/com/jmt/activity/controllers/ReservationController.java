package com.jmt.activity.controllers;

import com.jmt.activity.entities.Reservation;
import com.jmt.activity.services.ReservationApplyService;
import com.jmt.activity.services.ReservationInfoService;
import com.jmt.activity.validators.ReservationValidator;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.BadRequestException;
import com.jmt.global.rests.JSONData;
import com.jmt.member.MemberUtil;
import com.jmt.member.entities.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationApplyService applyService;
    private final ReservationInfoService infoService;
    private final ReservationValidator validator;
    private final MemberUtil memberUtil;
    private final Utils utils;

    /**
     * 예약 접수
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<JSONData> apply(@RequestBody @Valid RequestReservation form, Errors errors) {
        validator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        Reservation reservation = applyService.apply(form);

        HttpStatus status = HttpStatus.CREATED;
        JSONData jsonData = new JSONData(reservation);
        jsonData.setStatus(status);

        return ResponseEntity.status(status).body(jsonData);
    }

    @GetMapping
    public JSONData list(@ModelAttribute ReservationSearch search) {
        Member member = memberUtil.getMember();
        search.setMemberSeqs(List.of(member.getSeq()));

        ListData<Reservation> data = infoService.getList(search);

        return new JSONData(data);
    }


}
