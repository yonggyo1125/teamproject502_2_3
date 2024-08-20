package com.jmt.email.services;

import com.jmt.email.controllers.EmailMessage;
import com.jmt.email.entities.AuthNum;
import com.jmt.email.repositories.AuthNumRepository;
import com.jmt.global.Utils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailSendService sendService;
    private final HttpSession session;
    private final Utils utils;
    private final AuthNumRepository authNumRepository;

    /**
     * 이메일 인증 번호 발급 전송
     *
     * @param email
     * @return
     */
    public boolean sendCode(String email, String uid) {
        if (!StringUtils.hasText(uid)) {
            return false;
        }

        int authNum = (int)(Math.random() * 99999);

        session.setAttribute("EmailAuthNum", authNum);
        session.setAttribute("EmailAuthStart", System.currentTimeMillis());

        EmailMessage emailMessage = new EmailMessage(
                email,
                utils.getMessage("Email.verification.subject"),
                utils.getMessage("Email.verification.message"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", authNum);

        /* 레디스에 발급 받은 정보 저장 S */
        AuthNum data = new AuthNum();
        data.setUid(uid);
        data.setAuthNum(authNum);
        authNumRepository.save(data);
        /* 레디스에 발급 받은 정보 저장 E */

        return sendService.sendMail(emailMessage, "auth", tplData);
    }

    /**
     * 발급 받은 인증번호와 사용자 입력 코드와 일치 여부 체크
     *
     * @param code
     * @return
     */
    public boolean check(int code, String uid) {

        if (!StringUtils.hasText(uid)) {
            return false;
        }

        AuthNum data = authNumRepository.findById(uid).orElse(null);
        if (data == null) {
            return false;
        }
        System.out.println("data: " + data);
        System.out.println("code: " + code);
        Integer authNum = data.getAuthNum();

        // 사용자 입력 코드와 발급 코드가 일치하는지 여부 체크
        return code == authNum.intValue();

    }
}
