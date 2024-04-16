package com.example.spring_yao.service;

import com.example.spring_yao.model.userbasic.UserBasicCrForm;
import org.springframework.http.ResponseEntity;

public interface LoggingService {

    /**
     * 新增註冊會員
     * @author Yao
     * @param userBasicCrForm
     * Last Modify author Yao Date: 2024/4/12 Ver:1.0
     * change Description
     * */
    ResponseEntity<String> setNewUser(UserBasicCrForm userBasicCrForm) throws Exception;

}
