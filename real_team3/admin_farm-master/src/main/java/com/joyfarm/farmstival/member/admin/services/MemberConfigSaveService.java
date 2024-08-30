package com.joyfarm.farmstival.member.admin.services;


import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.script.AlertException;
import com.joyfarm.farmstival.member.admin.controllers.RequestMember;
import com.joyfarm.farmstival.member.constants.Authority;
import com.joyfarm.farmstival.member.entities.Authorities;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {

    private final MemberRepository memberRepository;
    private final Utils utils;

    /* 특정 회원정보 폼에서 변경시 저장*/
    @Transactional
    public void save(RequestMember form){
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(form.getEmail()));

        System.out.println("Before save: " + member.getAuthorities());

        member.setUserName(form.getUserName());
        member.setEmail(form.getEmail());
        member.setMobile(form.getMobile());

        member.getAuthorities().clear(); //기존 권한 지우고 새로 설정

        List<String> strAuthorities = form.getAuthorities();

        List<Authorities> authoList = new ArrayList<>();
        for(String autho : strAuthorities){
            Authorities authorities = new Authorities();
            authorities.setMember(member);
            authorities.setAuthority(Authority.valueOf(autho));

            authoList.add(authorities);
        }
        member.getAuthorities().addAll(authoList);// 새로 생성한 authoList를 member에 설정

        memberRepository.saveAndFlush(member);

        System.out.println("After save: " + memberRepository.findByEmail(member.getEmail()).get().getAuthorities());

    }

    public void saveList(List<Integer> chks) {

        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String userEmail = utils.getParam("email_" + chk);

            Member member = memberRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new AlertException("해당 이메일의 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

            if(member == null) continue;
            System.out.println("Before save: " + member.getAuthorities());

            String strAuthority = utils.getParam("authority_" + chk); // 수정된 부분

            if (strAuthority == null || strAuthority.isEmpty()) {
                throw new AlertException("권한 값이 제공되지 않았습니다.", HttpStatus.BAD_REQUEST);
            }

            try {
                Authority authority = Authority.valueOf(strAuthority);
                Authorities authorities = new Authorities();
                authorities.setMember(member);
                authorities.setAuthority(authority);

                member.getAuthorities().clear(); // 기존 권한 제거
                member.getAuthorities().add(authorities);

                memberRepository.saveAndFlush(member);
                System.out.println("After save: " + memberRepository.findByEmail(member.getEmail()).get().getAuthorities());
            } catch (IllegalArgumentException e) {
                throw new AlertException("유효하지 않은 권한 값입니다.", HttpStatus.BAD_REQUEST);
            }
        }


        // 모든 수정된 사항을 데이터베이스에 반영
        memberRepository.flush();
    }
}
