package com.joyfarm.farmstival.member.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// 스프링 시큐리티 내에서 사용하는 필터는 별도의 방식이 있다. 여기서 정의하고 추가하면 된다.
// 스프링 시큐리티에서는 필터 전, 후 등 특정 시점에 추가할 수 있는 기능이 있다.
// 그 점을 활용해서 토큰으로 로그인을 하고 로그인을 유지하는 방식을 기본 필터 전에 먼저 추가할 것이다.
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider provider;

    /**
     * 요청 헤더 Authorization : Bearer JWT 토큰 값
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = getToken(request);
        if(StringUtils.hasText(token)) {
            // 토큰을 가지고 회원 인증 객체를 추출한 다음 로그인 유지 처리를 한다.
            Authentication authentication = provider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); // -> 이것을 넣으면 로그인 유지가 된다.
        }
        chain.doFilter(request, response);
    }

    /**
     * 요청 헤더에서 JWT 토큰 추출
     * Authorization : Bearer JWT 토큰 값
     * Bearer -> 토큰 인증방식
     *
     * @param request
     * @return
     */

    // 토큰을 추출할 수 있는 메서드 추가 (여기서 추출해서 헤더에 실어서 보낸다)
    private String getToken(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String bearerToken = req.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)
                && bearerToken.toUpperCase().startsWith("BEARER ")) {
            // bearer로 시작하는 토큰을 여기서 추출한다.
            // 이것은 정해져 있는 방식이다.
            // bearer 뒤부터 잘라서 추출한다.
            return bearerToken.substring(7).trim();
        }
        return null;
    }
}
