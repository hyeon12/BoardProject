package org.hyeon.main.controllers;

import org.hyeon.global.exceptions.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController implements ExceptionProcessor {
    @GetMapping //메인 경로
    public String index(){
        return "front/main/index";
    }
}
