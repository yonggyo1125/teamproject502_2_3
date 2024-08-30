package com.joyfarm.farmstival.member.services;

import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.member.controllers.RequestLogin;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Setter
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private Utils utils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();

        RequestLogin form = new RequestLogin();
        form.setEmail(request.getParameter("email"));
        form.setPassword(request.getParameter("password"));

        if (exception instanceof BadCredentialsException) {//아이디 또는 비밀번호가 일치 하지 않는 경우
            form.setCode("BadCredentials.Login");
        } else if (exception instanceof DisabledException) {//탈퇴한 회원
            form.setCode("Disabled.Login");
        } else if (exception instanceof CredentialsExpiredException) { // 비밀번호 유효기간 만료
            form.setCode("CredentialsExpired.Login");
        } else if (exception instanceof LockedException) { //사용자 계정이 잠겨있는 경우
            form.setCode("Locked.Login");
        } else {
            form.setCode("Fail.Login");
        }
        form.setDefaultMessage(exception.getMessage());
        System.out.println(exception);

        form.setSuccess(false);
        session.setAttribute("requestLogin", form);

        //로그인 실패시 로그인 페이지 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }

}
