package org.hyeon.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyeon.board.entities.Board;
import org.hyeon.board.repositories.BoardRepository;
import org.hyeon.global.exceptions.ExceptionProcessor;
import org.hyeon.member.MemberInfo;
import org.hyeon.member.MemberUtil;
import org.hyeon.member.services.MemberSaveService;
import org.hyeon.member.validators.JoinValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class MemberController implements ExceptionProcessor {
    // 템플릿 3개 구조로 구성
    // front : PC / mobile : 모바일(*) / admin : 관리자

    private final JoinValidator joinValidator;
    private final MemberSaveService memberSaveService;
    private final MemberUtil memberUtil;
    private final BoardRepository boardRepository;

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
//        memberSaveService.save(form);
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

    @ResponseBody
    @GetMapping("/test2")
    public void test2(@AuthenticationPrincipal MemberInfo memberInfo){
        log.info("로그인 아이디는 {} 입니다.", memberInfo.toString());
    }

    @ResponseBody
    @GetMapping("/test3")
    public void test3(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("로그인 상태 : {}", authentication.isAuthenticated());
        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberInfo) {
            // 로그인 상태 - UserDetails 구현체 (getPrincipal())
            MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
            log.info("로그인 회원 : {}", memberInfo.toString());
        } else {
            // 미로그인 상태 - String / anonymousUser (getPrincipal())
            log.info("getPrincipal(): {}", authentication.getPrincipal());
        }
    }

    @ResponseBody
    @GetMapping("/test4")
    public void test4(){
        log.info("로그인 상태: {}", memberUtil.isLogin());
        log.info("로그인 상태: {}", memberUtil.getMember());
    }

    @ResponseBody
    @GetMapping("/test5")
    public void test5(){

/*        Board board = Board.builder()
                .bId("freetalk")
                .bName("자유게시판")
                .build();

        boardRepository.saveAndFlush(board);*/
        Board board = boardRepository.findById("freetalk").orElse(null);
        board.setBName("(재수정)자유게시판");
        boardRepository.saveAndFlush(board);


    }
}
