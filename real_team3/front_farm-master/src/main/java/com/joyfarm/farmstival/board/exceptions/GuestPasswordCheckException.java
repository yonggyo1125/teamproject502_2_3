package com.joyfarm.farmstival.board.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super("RequiredCheck.guestPw", HttpStatus.UNAUTHORIZED);
        setErrorCode(true);
    }
}
