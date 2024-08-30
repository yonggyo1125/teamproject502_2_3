package com.joyfarm.farmstival.board.exceptions;


import com.joyfarm.farmstival.global.exceptions.script.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        super("NotFound.board", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
