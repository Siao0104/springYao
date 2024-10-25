package com.example.spring_yao.controller;

import com.example.spring_yao.service.MailService;
import com.example.spring_yao.utils.TokenGenerator;
import com.example.spring_yao.utils.jwt.TokenService;
import com.example.spring_yao.utils.jwt.loginreponse.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("springYao/Mail")
@Slf4j
@Tag(name = "Mail發送")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        stringRedisTemplate.opsForValue().set(newToken, to, 20, TimeUnit.MINUTES);
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
        String gmail = stringRedisTemplate.opsForValue().get(token);
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
    public void uiRedirectToGoogleOAuth(HttpServletResponse response, HttpSession session) throws IOException {
        // 產生一個隨機的 state 值
        String state = UUID.randomUUID().toString();
        // 將 state 存入 session 中，之後進行驗證
        session.setAttribute("oauth_state", state);

        String googleOAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=email%20profile&" +
                "response_type=code&" +
                "client_id=1088548958107-s41oj54rqnrpvv625t62pet2r0stq3fm.apps.googleusercontent.com&" +
                "redirect_uri=http://localhost:8080/springYao/Mail/uiGetGoogleUserEmail&" +
                "state=" +state;

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
    public void uiGetGoogleUserEmail(HttpServletResponse response,@RequestParam String code, @RequestParam String state, HttpSession session) throws Exception {
        // 從 session 中取得之前儲存的 state
        String sessionState = (String) session.getAttribute("oauth_state");

        // 驗證回傳的 state 是否與之前儲存的 state 相同
        if (!state.equals(sessionState)) {
            throw new SecurityException("可能存在 CSRF 攻擊，state 不匹配");
        } else {
            // 使用 Google OAuth 2.0 驗證碼取得 Access Token
            String accessToken = mailService.brGetGoogleAccessToken(code);

            // 使用 Google Access Token 獲取使用者的 Gmail 資訊
            String email = mailService.brGetUserEmail(accessToken);
            if (StringUtils.isNotEmpty(email)) {
                LoginResponse loginResponse = tokenService.createTokenForOauth(email);
                // 將 loginResponse 轉換為 JSON 字串
                ObjectMapper objectMapper = new ObjectMapper();
                String loginResponseJson = objectMapper.writeValueAsString(loginResponse);
                // URL 編碼 JSON 字串
                String encodedResponse = URLEncoder.encode(loginResponseJson, StandardCharsets.UTF_8.toString());
                response.sendRedirect("http://localhost:5173/googleOauth?accessToken="+encodedResponse);
            }
        }
    }
}
