package com.joyfarm.farmstival.file.exceptions;

import com.joyfarm.farmstival.global.exceptions.script.AlertBackException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends AlertBackException {
    public FileNotFoundException() {
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
