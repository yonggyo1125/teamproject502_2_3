package com.jmt.member.services;

import com.jmt.file.entities.FileInfo;
import com.jmt.file.services.FileInfoService;
import com.jmt.member.MemberInfo;
import com.jmt.member.constants.Authority;
import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.Member;
import com.jmt.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<Authorities> tmp = member.getAuthorities();
        if (tmp == null || tmp.isEmpty()) {
            tmp = List.of(Authorities.builder().member(member).authority(Authority.USER).build());
        }

        List<SimpleGrantedAuthority> authorities = tmp.stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority().name()))
                .toList();

        // 프로필 이미지 처리
        List<FileInfo> files = fileInfoService.getList(member.getGid());
        if (files != null && !files.isEmpty()) member.setProfile(files.get(0));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
