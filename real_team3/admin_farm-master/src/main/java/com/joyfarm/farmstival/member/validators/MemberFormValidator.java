package com.joyfarm.farmstival.member.validators;

import com.joyfarm.farmstival.member.admin.controllers.RequestMember;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberFormValidator implements Validator {
    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestMember.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //회원 이메일 중복 체크

        RequestMember form = (RequestMember) target;

        String email = form.getEmail();
        String mode = form.getMode();
        if(mode.equals("edit")&& StringUtils.hasText(email) && memberRepository.exists(email)){
            errors.rejectValue("email","Duplicated");
        }

    }
}
