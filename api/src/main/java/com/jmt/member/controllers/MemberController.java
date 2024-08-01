package com.jmt.member.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class MemberController {

    @GetMapping("/token")
    public String token() {

        return "token";
    }
}
