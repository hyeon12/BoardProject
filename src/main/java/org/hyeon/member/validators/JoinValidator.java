package org.hyeon.member.validators;

import org.hyeon.global.validators.PasswordValidator;
import org.hyeon.member.controllers.RequestJoin;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class JoinValidator implements Validator, PasswordValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        //검증하고자 하는 커맨드 객체의 제한
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){
            //커맨드 객체 검증 이미 실패한 경우 추가 검증X
            //null, size ...
           return;
        }

        /** 추가적인 검증 절차
         * 1. 이미 가입된 회원인지 체크 (이메일)
         * 2. password, confirmPassword 의 일치 여부
         * 3. 비밀번호 복잡성 체크
         * 4. 휴대전화번호 형식 체크
         */

        RequestJoin form = (RequestJoin) target; //target 지정!
        //검증에 필요한 검증 대상들 꺼내오기
        String email = form.getEmail();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String moblie = form.getMobile();

        // 1. 이미 가입된 회원인지 체크

        // 2. password, confirmPassword 의 일치 여부
        if(!password.equals(confirmPassword)){
            errors.rejectValue("confirmPassword", "MisMatch.password");
        }

        // 3. 비밀번호 복잡성 체크 - 알파벳 대소문자 각각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상
        if(!alphaCheck(password, false) || !numberCheck(password) || !specialCharsCheck(password)){
            errors.rejectValue("password", "Complexity");
        }

    }
}
