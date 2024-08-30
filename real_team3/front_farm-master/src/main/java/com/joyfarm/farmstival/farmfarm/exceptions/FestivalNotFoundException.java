package com.joyfarm.farmstival.farmfarm.exceptions;

import com.joyfarm.farmstival.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

// 조회된 축제 정보가 없을 때
public class FestivalNotFoundException extends CommonException {
    public FestivalNotFoundException(){
        super("NotFound.festival", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
