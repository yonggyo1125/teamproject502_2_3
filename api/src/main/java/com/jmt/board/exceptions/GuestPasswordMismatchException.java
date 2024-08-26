package com.jmt.board.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordMismatchException extends CommonException {
    public GuestPasswordMismatchException() {
        super("Mismatch.password", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
