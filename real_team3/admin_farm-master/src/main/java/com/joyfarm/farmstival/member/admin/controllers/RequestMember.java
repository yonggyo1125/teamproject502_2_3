package com.joyfarm.farmstival.member.admin.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestMember {
    private String mode= "edit";

    private boolean activity;

    private Long seq;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    private String password;
    @NotBlank
    private String mobile;
    private List<String> authorities = new ArrayList<>();
}
