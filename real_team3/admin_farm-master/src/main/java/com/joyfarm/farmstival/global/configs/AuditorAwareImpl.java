package com.joyfarm.farmstival.global.configs;


import com.joyfarm.farmstival.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AuditorAwareImpl 클래스는 AuditorAware<String>을 구현하여 현재 사용자(예: 로그인한 사용자)의 이메일 주소를 제공하는 역할을 한다.
 * AuditorAware: Entity의 생성 및 수정 정보를 자동으로 추적하고 기록하는 기능 제공
 */
@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    /* 로그인 시 회원정보에서 이메일을 가져오고 로그인 아닐시 null 값 */
    @Override
    public Optional<String> getCurrentAuditor() {
        String email = memberUtil.isLogin() ? memberUtil.getMember().getEmail() : null;
        //memberUtil.getMember() -> MemberInfo 클래스의 Member 객체의 getter 메서드로 email 정보 조회

        return Optional.ofNullable(email);
    }
}
