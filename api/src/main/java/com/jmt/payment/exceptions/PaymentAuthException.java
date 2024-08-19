package com.jmt.payment.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class PaymentAuthException extends CommonException {
    public PaymentAuthException(String message, String url) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
