package com.joyfarm.farmstival.board.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends CommonException {
    public BoardNotFoundException() {
        super("NotFound.board", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
