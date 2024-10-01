package com.example.spring_yao.controller;

import com.example.spring_yao.service.MailService;
import com.example.spring_yao.utils.TokenGenerator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("springYao/Mail")
@Slf4j
@Tag(name = "Mail發送")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 發送可驗證mail連結
     * @author Yao
     * @param to
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    @Operation(summary = "發送可驗證mail連結")
    @PostMapping("/uiSendValidateLink")
    public String uiSendValidateLink(@RequestParam String to) throws Exception {
        String newToken = TokenGenerator.generateToken();
        redisTemplate.opsForValue().set(newToken, to, 20, TimeUnit.MINUTES);
        mailService.brSendValidateLink(to,"驗證郵件登入",String.format("點擊下面的連結來驗證你的帳號 http://localhost:5173/mail?token=%s",newToken));
        return String.format("驗證郵件已發送至 %s",to);
    }

    /**
     * mail連結是否過期
     * @author Yao
     * @param token
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    @Operation(summary = "mail連結是否過期")
    @GetMapping("/uiCheckTokenDead")
    public ResponseEntity<String> uiCheckTokenDead(@RequestParam String token){
        String gmail = redisTemplate.opsForValue().get(token);
        if (gmail != null) {
            return new ResponseEntity<>("token有效", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("token已過期", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 重定向到 Google OAuth 2.0 驗證頁面
     * @author Yao
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    @Operation(summary = "重定向到 Google OAuth 2.0 驗證頁面")
    @GetMapping("/uiRedirectToGoogleOAuth")
    public void uiRedirectToGoogleOAuth(HttpServletResponse response) throws IOException {
        String googleOAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=email%20profile&" +
                "response_type=code&" +
                "client_id=1088548958107-s41oj54rqnrpvv625t62pet2r0stq3fm.apps.googleusercontent.com&" +
                "redirect_uri=http://localhost:8080/springYao/Mail/uiGetGoogleUserEmail&" +
                "state=custom_state"; // 可選擇性加入 state 以防 CSRF 攻擊

        // 重定向到 Google OAuth 2.0 驗證頁面
        response.sendRedirect(googleOAuthUrl);
    }

    /**
     * Google OAuth 2.0 驗證成功後，取得該使用者token
     * @author Yao
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    @Operation(summary = "Google OAuth 2.0 驗證成功後，取得該使用者token")
    @GetMapping("/uiGetGoogleUserEmail")
    public void uiGetGoogleUserEmail(@RequestParam String code) throws Exception {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        // 從 Google API Console 獲得 client_id 和 client_secret
        String clientId = "1088548958107-s41oj54rqnrpvv625t62pet2r0stq3fm.apps.googleusercontent.com";
        String clientSecret = "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";
        String redirectUri = "http://localhost:8080/springYao/Mail/uiGetGoogleUserEmail";

        // 請求 Google API 參數
        String requestBody = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                code, clientId, clientSecret, redirectUri);

        // RestTemplate 發送請求
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

        // 解析回傳的 JSON，取得 Google Access Token
        JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();

        // 使用 Google Access Token 獲取使用者的 Gmail 資訊
        mailService.brGetUserEmail(accessToken);
    }
}
