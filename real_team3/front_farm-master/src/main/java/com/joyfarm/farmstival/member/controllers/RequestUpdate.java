package com.joyfarm.farmstival.member.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestUpdate {

    private String password;

    private String confirmPassword;

    private String userName;

    private String mobile;
}
