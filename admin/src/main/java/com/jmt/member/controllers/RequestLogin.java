package com.jmt.member.controllers;

import lombok.Data;

@Data
public class RequestLogin {
    private String email;
    private String password;
}
