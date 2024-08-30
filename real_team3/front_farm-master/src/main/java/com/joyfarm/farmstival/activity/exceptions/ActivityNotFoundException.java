package com.joyfarm.farmstival.activity.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class ActivityNotFoundException extends CommonException {

    public ActivityNotFoundException() {
        super("NotFound.activity", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
