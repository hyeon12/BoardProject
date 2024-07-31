package org.hyeon.member;

import org.hyeon.member.constants.Authority;
import org.hyeon.member.entities.Authorities;
import org.hyeon.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberUtil { // 회원 편의 기능

    public boolean isLogin(){
        return getMember() != null;
    }

    public boolean isAdmin(){
        if(isLogin()){
            Member member = getMember();
            List<Authorities> authorities = member.getAuthorities();
            return authorities.stream().anyMatch(s -> s.getAuthority() == Authority.ADMIN);
            // 다중권한 : anyMatch - ADMIN 하나라도 포함되어 있으면, 참!
        }
        return false;
    }

    public Member getMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberInfo){
            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();

            return memberInfo.getMember();
        }
        return null;
    }
}
