package org.hyeon.global.exceptions;

import org.hyeon.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface RestExceptionProcessor {
// 레스트 컨트롤러일 때 추가!
    @ExceptionHandler(Exception.class)
    default ResponseEntity<JSONData> errorHandler(Exception e) {

        Object message = e.getMessage();

        // 커맨드 객체 검증 후, 발생하는 에러 메시지 출력 (기본적인 예외 발생 시)
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        if (e instanceof CommonException commonException) {
            status = commonException.getStatus();

            Map<String, List<String>> errorMessages = commonException.getErrorMessages();
            if (errorMessages != null) message = errorMessages;
        }

        JSONData data = new JSONData();
        data.setSuccess(false);
        data.setMessage(message);
        data.setStatus(status);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }

}
