package com.example.spring_yao.utils.jwt.loginrequest;

import lombok.Data;

@Data
public class LoginRequest {

    private String account;

    private String password;
}
