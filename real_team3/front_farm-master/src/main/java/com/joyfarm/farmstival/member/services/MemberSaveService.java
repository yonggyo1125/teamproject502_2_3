package com.joyfarm.farmstival.member.services;

import com.joyfarm.farmstival.member.MemberUtil;
import com.joyfarm.farmstival.member.constants.Authority;
import com.joyfarm.farmstival.member.controllers.RequestJoin;
import com.joyfarm.farmstival.member.controllers.RequestUpdate;
import com.joyfarm.farmstival.member.entities.Authorities;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.AuthoritiesRepository;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    /**
     * 회원 가입 처리
     *
     * @param form
     */
    public void save(RequestJoin form) {
        Member member = new ModelMapper().map(form, Member.class);
        String hash = passwordEncoder.encode(form.getPassword()); // BCrypt 해시화
        member.setPassword(hash);

        save(member, List.of(Authority.USER));
    }


    public void save(Member member, List<Authority> authorities) {

        // 휴대전화번호 숫자만 기록
        String mobile = member.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        String gid = member.getGid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();
        member.setGid(gid);

        memberRepository.saveAndFlush(member);

        // 권한 추가, 수정 S
        if (authorities != null) {
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            items = authorities.stream().map(a -> Authorities.builder()
                    .member(member)
                    .authority(a)
                    .build()).toList();

            authoritiesRepository.saveAllAndFlush(items);
        }
        // 권한 추가, 수정 E
    }

    public void updateMember(RequestUpdate form) {

        Member member = memberUtil.getMember();

        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            String hash = passwordEncoder.encode(form.getPassword());
            member.setPassword(hash); // 비밀번호는 암호화하여 저장해야 함
        }
        if (form.getUserName() != null && !form.getUserName().isBlank()) {
            member.setUserName(form.getUserName());
        }
        if (form.getMobile() != null && !form.getMobile().isBlank()) {
            member.setMobile(form.getMobile());
        }

        save(member, null);

    }

    public void withdraw(){
        Member member = memberUtil.getMember();

        if(memberUtil.isLogin()){
            //member.setDeletedAt(LocalDateTime.now());
            member.setDeletedAt(LocalDateTime.now());
        }

        save(member, null);
    }
}