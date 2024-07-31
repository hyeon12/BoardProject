package org.hyeon.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /** 로그인 성공 시 유입되는 핸들러! -> 로그인 성공 시 처리 해야 하는 작업들 정의
         Authentication - 로그인 사용자의 인증 정보 (권한 등급, 접근 권한, ..)
         */

        HttpSession session = request.getSession();
        // 세션에 남아 있는 requestLogin 값 제거
        session.removeAttribute("requestLogin");

        // 로그인 성공 시 - redirectUrl 이 있으면 해당 주소로 이동, 아니면 메인 페이지 이동
        String redirectUrl = request.getParameter("redirectUrl");
        redirectUrl = StringUtils.hasText(redirectUrl) ? redirectUrl.trim() : "/";

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
