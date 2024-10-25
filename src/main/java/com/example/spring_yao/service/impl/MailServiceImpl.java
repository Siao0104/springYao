package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.repository.UserBasicRepository;
import com.example.spring_yao.service.MailService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserBasicRepository userBasicRepository;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * 發送可驗證mail連結
     * @author Yao
     * @param to,subject,text
     * Last Modify author Yao Date: 2024/9/30 Ver:1.0
     * change Description
     * */
    public void brSendValidateLink(String to, String subject, String text) throws Exception {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(username);
            javaMailSender.send(message);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }

    /**
     * call Google API 取得 access token
     * @author Yao
     * @param code
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    public String brGetGoogleAccessToken(String code) throws Exception{
        try{
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
            return jsonObject.get("access_token").getAsString();
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }

    /**
     * 使用 Google Access Token 獲取使用者的 Gmail 資訊
     * @author Yao
     * @param token
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    public String brGetUserEmail(String token) throws Exception{
        try{
            String userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);

            // 解析回傳的 Gmail 資訊
            JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            String email = jsonObject.get("email").getAsString();
            return brCheckEmailLogin(email);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }

    /**
     * Google 驗證通過，查詢使用者的 Gmail 後直接登入
     * @author Yao
     * @param email
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    public String brCheckEmailLogin(String email) throws Exception{
        try{
            Object haveAccount = redisTemplate.opsForHash().get(email,"account");
            Object havePassword = redisTemplate.opsForHash().get(email,"password");
            if (haveAccount!= null && havePassword != null){
                return email;
            } else {
                UserBasicEntity user = userBasicRepository.getByEmail(email);
                if (user != null) {
                    redisTemplate.opsForHash().put(email,"account", user.getAccount());
                    redisTemplate.opsForHash().put(email,"password",user.getPassword());
                    redisTemplate.expire(email, 20, TimeUnit.MINUTES);
                    return email;
                } else {
                    return "";
                }
            }
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }
}
