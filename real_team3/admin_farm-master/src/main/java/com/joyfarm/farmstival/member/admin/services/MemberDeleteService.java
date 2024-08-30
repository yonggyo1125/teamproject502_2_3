package com.joyfarm.farmstival.member.admin.services;

import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.script.AlertException;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberDeleteService {

    private final MemberRepository memberRepository;
    private final AllMemberConfigInfoService memberConfigInfoService;
    private final Utils utils;

    /**
     * 회원 삭제
     */
    public void delete(String email) {
        Member member = memberConfigInfoService.get(email);

        if (member == null) {
            throw new AlertException("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        memberRepository.delete(member);
        memberRepository.flush();
    }

    /**
     * 회원 목록 삭제
     */
    public void deleteList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String email = utils.getParam("email_" + chk);
            try {
                delete(email);
            } catch (AlertException e) {
                log.error("Error deleting member with email: {}", email, e);
            }
        }
    }
}
