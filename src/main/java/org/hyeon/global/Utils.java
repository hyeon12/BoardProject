package org.hyeon.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils { // 빈의 이름 - utils

    private final MessageSource messageSource; // 메시지 가공
    private final HttpServletRequest request;

    public String toUpper(String str) {
        return str.toUpperCase();
    }

    public Map<String, List<String>> getErrorMessages(Errors errors) {
        // 커맨드 객체에서 온 Errors 객체 - JSON 형태로 가공해서 내보내야 함!

        // FieldErrors - 에러가 여러 개일수도 있으므로, List!
        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes())));

        // GlobalErrors
        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

        if (!gMessages.isEmpty()) {
            messages.put("global", gMessages);
        }
        return messages;
    }


    public List<String> getCodeMessages(String[] codes) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);
        //메시지가 없을 때 기본 코드 출력 - 실제 코드로 등록된 것만 나오도록 하기 위해 false

        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        return ms.getMessage(c, null, request.getLocale());
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .toList();

        ms.setUseCodeAsDefaultMessage(true);
        //싱글톤 형태로 false로 설정했으니, 다시 리셋해줘야 함!
        return messages;
    }

    //회원가입 쪽 메시지 조회하는 기능
    public String getMessage(String code){
        List<String> messages = getCodeMessages(new String[] {code});

        return messages.isEmpty() ? code : messages.get(0);
        // 없을 때는 코드 반환, 있을 때는 첫번재 메시지 반환
    }
}
