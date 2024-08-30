package com.joyfarm.farmstival.board.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommonException {
    public CommentNotFoundException(){
        super("NotFound.comment", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
