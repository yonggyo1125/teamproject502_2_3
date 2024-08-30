package com.joyfarm.farmstival.board.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordMismatchException extends CommonException {
    public GuestPasswordMismatchException() {
        super("Mismatch.password", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
