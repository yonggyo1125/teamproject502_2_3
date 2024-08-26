package com.jmt.board.validators;

import com.jmt.board.controllers.RequestComment;
import com.jmt.global.validators.PasswordValidator;
import com.jmt.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CommentValidator implements Validator, PasswordValidator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestComment.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (memberUtil.isLogin()) { // 로그인 상태 일때는 비회원 비밀번호 체크 X
            return;
        }

        RequestComment form = (RequestComment) target;

        String guestPw = form.getGuestPw();
        if (!StringUtils.hasText(guestPw)) {
            errors.rejectValue("guestPw", "NotBlank");
        }

        if (StringUtils.hasText(guestPw)) {
            if (guestPw.length() < 6) {
                errors.rejectValue("guestPw", "Size");
            }

            if (!alphaCheck(guestPw, true) || !numberCheck(guestPw)) {
                errors.rejectValue("guestPw", "Complexity");
            }
        } // endif
    }
}