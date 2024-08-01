package org.hyeon.global.exceptions.script;

import org.hyeon.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class AlertException extends CommonException {
    // 자바 스크립트 형태로 AlertMessage 출력
    public AlertException(String message, HttpStatus status){

        super(message, status);
    }
}
