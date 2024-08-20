package com.jmt.reservation.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends CommonException {
    public ReservationNotFoundException() {
        super("NotFound.reservation", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
