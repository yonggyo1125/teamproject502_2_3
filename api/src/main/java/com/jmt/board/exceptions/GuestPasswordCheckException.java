package com.jmt.board.exceptions;

import com.jmt.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super("비회원 비밀번호 인증 필요", HttpStatus.UNAUTHORIZED);
    }
}
