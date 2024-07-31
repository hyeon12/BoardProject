package org.hyeon.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hyeon.member.controllers.RequestLogin;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    // 로그인 실패시에 유입되는 메서드
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();

        RequestLogin form = new RequestLogin();
        form.setEmail(request.getParameter("email"));
        form.setPassword(request.getParameter("password"));

        if(exception instanceof BadCredentialsException){ //아이디 또는 비밀번호가 일치하지 않는 경우
            form.setCode("BadCredentials.Login");
        } else if (exception instanceof DisabledException){ // 탈퇴한 회원
            form.setCode("Disabled.Login");
        } else if (exception instanceof CredentialsExpiredException){ // 비밀번호 유효기간 만료
            form.setCode("CredentialsExpired.Login");
        } else if (exception instanceof AccountExpiredException){ // 사용자 계정 유효기간 만료
            form.setCode("AccountExpired.Login");
        } else if (exception instanceof LockedException){ // 사용자 계정이 잠겨있는 경우
            form.setCode("Locked.Login");
        } else {
            form.setCode("Fail.Login"); // 기타 예외
        }

        form.setDefaultMessage(exception.getMessage());
        // 설정된 메시지가 없는 경우, 기본 설정된 메시지가 출력될 수 있도록 처리!

        System.out.println(exception);

        form.setSuccess(false); //false 일때만 에러 메시지 호출
        session.setAttribute("requestLogin", form);
        // "requestLogin" 명칭으로 모델 쪽에 통합됨

        // 로그인 실패 시 로그인 페이지 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}