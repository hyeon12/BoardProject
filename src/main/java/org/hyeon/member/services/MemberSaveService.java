package org.hyeon.member.services;

import lombok.RequiredArgsConstructor;
import org.hyeon.member.constants.Authority;
import org.hyeon.member.controllers.RequestJoin;
import org.hyeon.member.entities.Authorities;
import org.hyeon.member.entities.Member;
import org.hyeon.member.repositories.AuthoritiesRepository;
import org.hyeon.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    /***
     * 회원 가입 처리
     *
     * @param form
     */
    public void save(RequestJoin form){
        Member member = new ModelMapper().map(form, Member.class);
        // setter, getter 번갈아 쓰기 복잡함 - ModelMapper로 자동 치환
        String hash = passwordEncoder.encode(form.getPassword());
        member.setPassword(hash);

        //일단 사용자 권한 부여 상태로 하지만,
        save(member, List.of(Authority.USER)); //내부에 saveAndFlush
    }

    /** 메서드 오버 로드
     *
     * @param member
     * @param authorities
     */
    public void save(Member member, List<Authority> authorities){

        //휴대전화번호 숫자만 기록
        String mobile = member.getMobile();
        if(StringUtils.hasText(mobile)){
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        memberRepository.saveAndFlush(member);
        /* 권한 추가, 수정 S */
        if(authorities != null) {
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items); //권한 비우기!
            authoritiesRepository.flush();
            items = authorities.stream()
                    .map(a -> Authorities.builder()
                                        .member(member)
                                        .authority(a)
                                        .build()).toList();

            authoritiesRepository.saveAllAndFlush(items);
        }
        /* 권한 추가, 수정 E */
    }

}
