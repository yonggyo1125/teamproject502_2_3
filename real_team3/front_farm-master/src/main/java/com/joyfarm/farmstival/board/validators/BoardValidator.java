package com.joyfarm.farmstival.board.validators;

import com.joyfarm.farmstival.board.controllers.RequestBoard;
import com.joyfarm.farmstival.global.validators.PasswordValidator;
import com.joyfarm.farmstival.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/*게시글 등록, 수정 시 검증*/
@Component
@RequiredArgsConstructor
public class BoardValidator implements Validator, PasswordValidator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBoard.class); //검증 객체
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestBoard form =(RequestBoard) target;

        String bid = form.getBid();
        Long seq = form.getSeq();
        if (seq == null && !StringUtils.hasText(bid)) {
            errors.rejectValue("bid", "NotBlank");
        }

        //비회원 비밀번호 유효성 검사
        if(!memberUtil.isLogin()){
            String guestPw = form.getGuestPw();
            if(!StringUtils.hasText(guestPw)){
                //비회원 비밀번호가 없을때
                errors.rejectValue("guestPw","NotBlank");
            }else {
                /**
                 * 비밀번호 복잡성
                 * 1. 자리수는? 4자리 이상
                 * 2. 숫자 + 알파벳
                 */
                if(guestPw.length() <4){
                    errors.rejectValue("guestPw", "Size");
                }
                if(!numberCheck(guestPw) || !alphaCheck(guestPw, true)){
                    errors.rejectValue("guestPw","Complexity");
                }
            }
        }
        /**
         * 글 수정 모드인 경우에는 seq 필수
         */
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "write";
        if(mode.equals("update") && (form.getSeq() == null || form.getSeq() < 1L) ){
            errors.rejectValue("seq","NotBlank");
        }

        // 공지글은 관리자만 작성 가능, 관리자 아닌 경우 false로 고정
        if(!memberUtil.isAdmin()){
            form.setNotice(false);
        }
    }
}
