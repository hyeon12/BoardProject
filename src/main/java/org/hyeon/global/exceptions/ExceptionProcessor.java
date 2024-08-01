package org.hyeon.global.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.hyeon.global.exceptions.script.AlertBackException;
import org.hyeon.global.exceptions.script.AlertException;
import org.hyeon.global.exceptions.script.AlertRedirectException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

public interface ExceptionProcessor {
    @ExceptionHandler(Exception.class)
    default ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 기본 응답 코드 500
        String tpl = "error/error";

        if (e instanceof CommonException commonException) {
            status = commonException.getStatus();

            if (e instanceof AlertException) {
                tpl = "common/_execute_script";
                String script = String.format("alert('%s');", e.getMessage());

                if (e instanceof AlertBackException alertBackException) {
                    script += String.format("%s.history.back();", alertBackException.getTarget());
                }

                if (e instanceof AlertRedirectException alertRedirectException) {
                    String url = alertRedirectException.getUrl();
                    // 외부 주소일 경우 contextPath 붙일 필요 없다!
                    if(!url.startsWith("http")){ // 외부 URL 이 아닌 경우
                        url = request.getContextPath() + url;
                    }

                    script += String.format("%s.location.replace('%s');", alertRedirectException.getTarget(), url);
                }
            }
        } else if (e instanceof AccessDeniedException){
                status = HttpStatus.UNAUTHORIZED;
        }

        String url = request.getRequestURI();
        String qs = request.getQueryString();

        if (StringUtils.hasText(qs)) url += "?" + qs;


        mv.addObject("message", e.getMessage());
        mv.addObject("status", status.value());
        mv.addObject("method", request.getMethod());
        mv.addObject("path", url);
        mv.setStatus(status);
        mv.setViewName(tpl);


        return mv;
    }
}