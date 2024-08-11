package com.diev.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dia")
public class DiaController {

    @GetMapping("/")
    public String diaHome() {
        return "dia/dia_home";
    }
}
