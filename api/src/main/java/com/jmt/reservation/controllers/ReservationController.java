package com.jmt.reservation.controllers;

import com.jmt.global.Utils;
import com.jmt.global.exceptions.BadRequestException;
import com.jmt.reservation.services.ReservationSaveService;
import com.jmt.reservation.validators.ReservationValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationValidator validator;
    private final ReservationSaveService saveService;
    private final Utils utils;

    @PostMapping("/apply")
    public void apply(@Valid @RequestBody RequestReservation form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        // 결제시 필요한 데이터 반환

    }
}
