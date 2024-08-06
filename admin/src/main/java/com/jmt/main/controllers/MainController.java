package com.jmt.main.controllers;

import com.jmt.global.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final Utils utils;
    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        System.out.println(request.getHeader("from-gateway"));
        model.addAttribute("addCss", List.of("member/test1", "member/test2"));
        model.addAttribute("addScript", List.of("member/test1", "member/test2"));

        return "main/index";
    }
    /*
    @GetMapping("/main2")
    public String index2() {

        //return utils.redirectUrl("/test2");
    }
    */
    @ResponseBody
    @GetMapping("/test2")
    public void test2() {

    }
}
