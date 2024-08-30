package com.joyfarm.farmstival.board.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotFoundException extends CommonException {
    public BoardDataNotFoundException() {
        super("NotFound.boardData", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
