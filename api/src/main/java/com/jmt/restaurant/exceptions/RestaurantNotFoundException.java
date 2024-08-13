package com.jmt.restaurant.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class RestaurantNotFoundException extends CommonException {
    public RestaurantNotFoundException() {
        super("NotFound.restaurant", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
