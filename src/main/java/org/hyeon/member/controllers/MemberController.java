package org.hyeon.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hyeon.member.validators.JoinValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    // 템플릿 3개 구조로 구성
    // front : PC / mobile : 모바일(*) / admin : 관리자

    private final JoinValidator joinValidator;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form){
        return "front/member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors){

        joinValidator.validate(form, errors); //에러 처리에 대한 서비스 추가~

        if(errors.hasErrors()){
            return "front/member/join";
        }

        return "redirect:/member/login";
    }

    //loginPs - 처리 과정은 시큐리티가!
    @GetMapping("/login")
    public String login(){
        return "front/member/login";
    }
}
