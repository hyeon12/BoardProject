package org.hyeon.global.configs;

import org.hyeon.member.services.LoginFailureHandler;
import org.hyeon.member.services.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//시큐리티 : 인가
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        /* 로그인, 로그아웃 S */
        http.formLogin(f -> {
            f.loginPage("/member/login") // 로그인 데이터를 제출할 url
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler());
        });

        //f ? form
        http.logout(f -> {
            f.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
            //처리할 작업이 없고, 주소 이동만 하는 경우 handler 대신 url 로 입력해주면 된다~!
        });
        /* 로그인, 로그아웃 E */

        /* 인가(접근 통제) 설정 S */
        http.authorizeHttpRequests(c -> {
           c.requestMatchers("/mypage/**").authenticated() //회원 전용 페이지 구성(일부)
                   .requestMatchers("/admin/**").hasAnyAuthority("ADMIN") //관리자 전용
                   .anyRequest().permitAll(); // 모든 페이지 공개
        });
        /* 인가(접근 통제) 설정 E */

        return http.build(); // 시큐리티 설정 무력화
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //Bcrypt 관련 메서드 O (encode, matches...)
    }
}
