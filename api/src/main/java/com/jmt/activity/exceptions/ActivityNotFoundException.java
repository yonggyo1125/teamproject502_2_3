package com.jmt.activity.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class ActivityNotFoundException extends CommonException {
    public ActivityNotFoundException() {
        super("NotFound.activity", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
