package com.jmt.global.configs;

import com.jmt.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = memberUtil.isLogin() ? memberUtil.getMember().getEmail() : null;
        return Optional.ofNullable(email);
    }
}