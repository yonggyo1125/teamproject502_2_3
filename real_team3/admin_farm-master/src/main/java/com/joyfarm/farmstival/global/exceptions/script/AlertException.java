package com.joyfarm.farmstival.global.exceptions.script;


import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class AlertException extends CommonException {
    public AlertException(String message, HttpStatus status) {
        super(message, status);
    }
}
