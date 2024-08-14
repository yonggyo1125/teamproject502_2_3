package com.jmt.tour.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class TourPlaceNotFoundException extends CommonException {
    public TourPlaceNotFoundException() {
        super("NotFound.tourPlace", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
