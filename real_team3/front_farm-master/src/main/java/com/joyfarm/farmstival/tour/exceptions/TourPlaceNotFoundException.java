package com.joyfarm.farmstival.tour.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class TourPlaceNotFoundException extends CommonException {
    public TourPlaceNotFoundException() {
        super("NotFound.TourPlace", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
