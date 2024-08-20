package com.jmt.payment.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class PaymentApprovalException extends CommonException {
    public PaymentApprovalException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
