package com.example.spring_yao.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    // 安全隨機數產生器
    private static final SecureRandom secureRandom = new SecureRandom();
    // 使用Base64進行編碼
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        // 產生隨機位元組
        secureRandom.nextBytes(randomBytes);
        // 返回經過編碼的 token
        return base64Encoder.encodeToString(randomBytes);
    }
}
