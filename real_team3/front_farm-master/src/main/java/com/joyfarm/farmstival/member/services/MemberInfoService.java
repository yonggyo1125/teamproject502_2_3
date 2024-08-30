package com.joyfarm.farmstival.member.services;

import com.joyfarm.farmstival.file.entities.FileInfo;
import com.joyfarm.farmstival.file.services.FileInfoService;
import com.joyfarm.farmstival.member.MemberInfo;
import com.joyfarm.farmstival.member.constants.Authority;
import com.joyfarm.farmstival.member.entities.Authorities;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 스프링 시큐리티에서 유저의 정보를 불러오기 위해 구현
 */
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;

    /* 회원 정보가 필요할때마다 호출되는 메서드 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//유저의 정보를 불러와서 UserDetails로 리턴

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)); //회원 없을 경우 예외 발생

        /* 로그인 막는 코드
        if(member.getDeletedAt() == null){
            throw new UsernameNotFoundException(username + "님은 탈퇴한 회원입니다.");
        }*/

        /*
        MemberInfo쪽에 getAuthorities()메서드를 통해서 사용자 권한 조회,
        권한이 null이거나 비어있을 때 대체 할 기본권한 -> USER, null이 아닌 경우 기존 권한 그대로 반환
         */
        List<Authorities> tmp = member.getAuthorities();
        if(tmp == null || tmp.isEmpty()){
            tmp = List.of(Authorities.builder().member(member).authority(Authority.USER).build());
        }

        /*
        tmp에서 가져온 Authorities 객체 리스트를 Spring Security가 이해할 수 있는 SimpleGrantedAuthority 객체 리스트로 변환하는 가공 단계가 필요하다.
         */
        List<SimpleGrantedAuthority> authorities = tmp.stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority().name()))
                .toList();//Authority enum의 name 메서드를 호출하여 문자열로 변환해야한다.(authority는 enum상수로 되어있기 때문!)

        addInfo(member); //gid로 가져온 파일 정보!

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    //파일 정보를 gid로 가져옴
    public void addInfo(Member member) {
        String gid = member.getGid();
        List<FileInfo> files = fileInfoService.getList(gid);
        if(files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }
    }
}
