package com.joyfarm.farmstival.activity.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;

public class ReservationNotFoundException extends CommonException {

    public ReservationNotFoundException() {
        super("NotFound.reservation");
        setErrorCode(true);
    }
}
