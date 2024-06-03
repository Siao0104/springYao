package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.utils.jwt.useridentity.UserIdentity;
import com.example.spring_yao.model.userbasic.UserBasicCrForm;
import com.example.spring_yao.repository.UserBasicRepository;
import com.example.spring_yao.service.LoggingService;
import com.example.spring_yao.utils.JsonUtils;
import com.example.spring_yao.utils.jwt.userauthority.UserAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private UserBasicRepository userBasicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserIdentity userIdentity;

    /**
     * 新增註冊會員
     * @author Yao
     * @param userBasicCrForm
     * Last Modify author Yao Date: 2024/4/12 Ver:1.0
     * change Description
     * */
    public ResponseEntity<String> setNewUser(UserBasicCrForm userBasicCrForm) throws Exception{
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR,10);
            Date dateIn10Years = calendar.getTime();
            var encodedPwd = passwordEncoder.encode(userBasicCrForm.getPassword());
            userBasicCrForm.setPassword(encodedPwd);
            UserBasicEntity userBasicEntity = JsonUtils.objectToObject(userBasicCrForm,UserBasicEntity.class);
            userBasicEntity.setCreatedBy(userIdentity.getAccount());
            userBasicEntity.setAuthority(UserAuthority.GUEST);
            userBasicEntity.setEnabled(true);
            userBasicEntity.setExpiryDate(dateIn10Years);
            userBasicRepository.save(userBasicEntity);
            return new ResponseEntity<>("註冊會員成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return new ResponseEntity<>("註冊會員失敗", HttpStatus.BAD_REQUEST);
        }
    }
}
