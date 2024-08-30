package com.joyfarm.farmstival.global.advices;

import com.joyfarm.farmstival.member.MemberUtil;
import com.joyfarm.farmstival.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice("com.joyfarm.farmstival") //컨트롤러에서 공통적으로 적용할 패키지 범위 설정
public class CommonControllerAdvice {
    /*로그인한 사용자 정보, 로그인 상태, 관리자 권한 여부를 모든 컨트롤러에서 공통적으로 사용할 수 있도록 설정*/

    private final MemberUtil memberUtil;

    @ModelAttribute("loggedMember") // 해당 이름으로 뷰에서 사용 할 수 있도록 모델에 추가
    public Member loggedMember() { // 현재 로그인 한 사용자의 정보
        return memberUtil.getMember(); //템플릿에서 ${loggedMember}로 접근 가능
    }

    @ModelAttribute("isLogin")
    public boolean isLogin() { // 로그인 상태 확인
        return memberUtil.isLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() { //관리자 권한 여부 확인
        return memberUtil.isAdmin();
    }
}