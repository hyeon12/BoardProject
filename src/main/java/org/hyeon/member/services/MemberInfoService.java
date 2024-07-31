package org.hyeon.member.services;

import lombok.RequiredArgsConstructor;
import org.hyeon.member.MemberInfo;
import org.hyeon.member.constants.Authority;
import org.hyeon.member.entities.Authorities;
import org.hyeon.member.entities.Member;
import org.hyeon.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //회원 정보 조회 후, 정보가 없을 때는 예외 처리 - orElseThrow 매개변수 Supplier
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        //권한 null 값일 때 권한을 USER 로 부여
        List<Authorities> tmp = Objects.requireNonNullElse(member.getAuthorities(),
                List.of(Authorities.builder().member(member).authority(Authority.USER).build()));

        List<SimpleGrantedAuthority> authorities = tmp.stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority().name()))
                .toList();


        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member) // 이메일, 비밀번호 외 기타 회원 정보
                .authorities(authorities)
                .build();
    }
}
