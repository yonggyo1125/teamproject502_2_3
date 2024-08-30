package com.joyfarm.farmstival.memberManage;

import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.member.admin.services.AllMemberConfigInfoService;
import com.joyfarm.farmstival.member.controllers.MemberSearch;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("test")
public class MemberInfoServiceTest {

    @Autowired
    private AllMemberConfigInfoService memberConfigInfoService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void init(){

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@test.org")
                    .password("_aA123456")
                    .userName("사용자" + i)
                    .mobile("010-1111-222" + i)
                    .build();
            memberRepository.saveAndFlush(member);
        });
    }

    @Test
    void infoTest(){
        MemberSearch search = new MemberSearch();
        search.setPage(1);
        search.setLimit(10);

        // 서비스 메서드 호출
        ListData<Member> data = memberConfigInfoService.getList(search);

        // 결과 출력
        List<Member> items = data.getItems();
        items.forEach(member -> {
            System.out.println("Email: " + member.getEmail());
            System.out.println("UserName: " + member.getUserName());
            System.out.println("Mobile: " + member.getMobile());
            System.out.println("----------------------");
        });
    }

}
