package com.jmt.mypage.controllers;

import com.jmt.global.Utils;
import com.jmt.global.exceptions.BadRequestException;
import com.jmt.global.rests.JSONData;
import com.jmt.member.MemberUtil;
import com.jmt.member.entities.Member;
import com.jmt.member.services.MemberSaveService;
import com.jmt.mypage.validators.ProfileUpdateValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MemberSaveService memberSaveService;
    private final ProfileUpdateValidator profileUpdateValidator;
    private final MemberUtil memberUtil;
    private final Utils utils;


    @PatchMapping("/profile")
    public JSONData profileUpdate(@RequestBody @Valid RequestProfile form, Errors errors) {

        profileUpdateValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        memberSaveService.save(form);

        Member member = memberUtil.getMember();

        return new JSONData(member);
    }
}
