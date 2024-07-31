package org.hyeon.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyeon.member.services.MemberSaveService;
import org.hyeon.member.validators.JoinValidator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("requestLogin")
public class MemberController {
    // 템플릿 3개 구조로 구성
    // front : PC / mobile : 모바일(*) / admin : 관리자

    private final JoinValidator joinValidator;
    private final MemberSaveService memberSaveService;

    @ModelAttribute //해당 값이 세션 범위 내에서 유지 된다!
    public RequestLogin requestLogin(){
        return new RequestLogin();
    }

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

        memberSaveService.save(form); // 회원 가입 처리

        return "redirect:/member/login";
    }

    //loginPs - 처리 과정은 시큐리티가!
    @GetMapping("/login")
    public String login(@Valid @ModelAttribute RequestLogin form, Errors errors){
        String code = form.getCode();
        if(StringUtils.hasText(code)){
            errors.reject(code, form.getDefaultMessage());
            if (code.equals("CredentialsExpired.Login")){
                return "redirect:/member/password/reset"; //비밀번호 초기화 페이지로
            }
        }
        return "front/member/login";
    }

    @ResponseBody //반환값 없는 경우에는 responseBody
    @GetMapping("/test")
    public void test(Principal principal){
        // 로그인 한 회원정보를 알 수 있음!
        log.info("로그인 아이디는 {} 입니다.", principal.getName());
    }
}
