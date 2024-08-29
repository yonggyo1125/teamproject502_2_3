package com.jmt.member.services;

import com.jmt.file.services.FileUploadDoneService;
import com.jmt.member.MemberUtil;
import com.jmt.member.constants.Authority;
import com.jmt.member.controllers.RequestJoin;
import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.Member;
import com.jmt.member.exceptions.MemberNotFoundException;
import com.jmt.member.repositories.AuthoritiesRepository;
import com.jmt.member.repositories.MemberRepository;
import com.jmt.mypage.controllers.RequestProfile;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadDoneService doneService;
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

    /**
     * 회원정보 수정
     * @param form
     */
    public void save(RequestProfile form) {
        Member member = memberUtil.getMember();
        String email = member.getEmail();
        member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String password = form.getPassword();
        String mobile = form.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
        }

        member.setUserName(form.getUserName());
        member.setMobile(mobile);

        if (StringUtils.hasText(password)) {
            String hash = passwordEncoder.encode(password);
            member.setPassword(hash);
        }

        save(member, null);
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

        doneService.process(gid); // 파일 업로드 완료 처리

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
}
