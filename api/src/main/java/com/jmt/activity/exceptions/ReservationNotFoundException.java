package com.jmt.activity.exceptions;

import com.jmt.global.exceptions.CommonException;

public class ReservationNotFoundException extends CommonException {
    public ReservationNotFoundException() {
        super("NotFound.reservation");
        setErrorCode(true);
    }
}
