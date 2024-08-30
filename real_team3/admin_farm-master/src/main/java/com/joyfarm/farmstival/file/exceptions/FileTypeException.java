package com.joyfarm.farmstival.file.exceptions;


import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class FileTypeException extends CommonException {
    public FileTypeException(HttpStatus status) {
        super("FileType", status);
        setErrorCode(true);
    }
}
