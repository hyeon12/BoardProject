package org.hyeon.global.advices;

import lombok.RequiredArgsConstructor;
import org.hyeon.member.MemberUtil;
import org.hyeon.member.entities.Member;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice("org.hyeon") //적용 범위
public class CommonControllerAdvice {
    // 모든 컨트롤러에 적용되며, 컨트롤러 실행 전에 반영됨

    private final MemberUtil memberUtil;

    @ModelAttribute("loggedMember")
    public Member loggedMember(){
        return memberUtil.getMember();
    }

    @ModelAttribute("isLogin")
    public boolean isLogin(){
        return memberUtil.isLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(){
        return memberUtil.isAdmin();
    }
}
