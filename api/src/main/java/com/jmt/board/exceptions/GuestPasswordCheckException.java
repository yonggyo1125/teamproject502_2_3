package com.jmt.board.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super("RequiredCheck.guestPw", HttpStatus.UNAUTHORIZED);
        setErrorCode(true);
    }
}
