package org.hyeon.global.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true);
        //메세지 코드가 없는 경우! 오류 대신 메세지를 코드로 대체할 수 있도록
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors");

        return ms;
    }
}
