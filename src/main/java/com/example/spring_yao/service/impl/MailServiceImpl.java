package com.example.spring_yao.service.impl;

import com.example.spring_yao.service.MailService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

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
     * 使用 Google Access Token 獲取使用者的 Gmail 資訊
     * @author Yao
     * @param token
     * Last Modify author Yao Date: 2024/10/1 Ver:1.0
     * change Description
     * */
    public void brGetUserEmail(String token) throws Exception{
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
            System.out.println(email+"email------------------------------------");
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }
}
