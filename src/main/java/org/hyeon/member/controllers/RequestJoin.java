package org.hyeon.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 커맨드 객체? 사용자가 보낸 데이터를 "전달" 받는 객체 DTO
@Data
public class RequestJoin {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String mobile;

    @AssertTrue // (필수)true 여야만 검증 통과
    private boolean agree;
}
