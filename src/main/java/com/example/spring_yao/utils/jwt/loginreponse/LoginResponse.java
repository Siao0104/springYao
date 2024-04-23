package com.example.spring_yao.utils.jwt.loginreponse;

import com.example.spring_yao.utils.jwt.userauthority.UserAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private String userName;

    private String account;

    private UserAuthority userAuthority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date accessTokenDeadLine;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date refreshTokenDeadLine;
}
