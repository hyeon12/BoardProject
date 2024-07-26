package org.hyeon.member.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    // front : PC / mobile : 모바일 / admin : 관리자

    @GetMapping("/join")
    public String join(){
        return "front/member/join";
    }

    @PostMapping("/join")
    public String joinPs(){
        return "redirect:/member/login";
    }

    //loginPs - 처리 과정은 시큐리티가!
    @GetMapping("/login")
    public String login(){
        return "front/member/login";
    }
}
