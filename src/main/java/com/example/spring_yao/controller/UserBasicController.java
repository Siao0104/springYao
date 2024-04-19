package com.example.spring_yao.controller;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.jwt.TokenService;
import com.example.spring_yao.jwt.loginreponse.LoginResponse;
import com.example.spring_yao.jwt.loginrequest.LoginRequest;
import com.example.spring_yao.model.userbasic.UserBasicCrForm;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import com.example.spring_yao.repository.UserBasicRepository;
import com.example.spring_yao.service.LoggingService;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("spring_yao/userBasic")
@Slf4j
@Tag(name = "註冊會員主檔")
public class UserBasicController {

    @Autowired
    private UserBasicRepository userBasicRepository;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "取得全部註冊會員資料")
    @GetMapping("/uiGetAllUser/{account}")
    public ResponseEntity<List<UserBasicListVO>> uiGetAllUser(@PathVariable("account") String account) {
        List<UserBasicEntity> userBasicEntities = userBasicRepository.getAllByAccount(account);
        List<UserBasicListVO> userBasicListVOS = JsonUtils.listTolist(userBasicEntities,UserBasicListVO.class);
        return ResponseEntity.ok(userBasicListVOS);
    }

    @Operation(summary = "註冊會員資料")
    @PostMapping("/uiRegisterUser")
    public ResponseEntity<String> uiRegisterUser(@RequestBody @Valid UserBasicCrForm userBasicCrForm) throws Exception{
        return loggingService.setNewUser(userBasicCrForm);
    }

    @Operation(summary = "會員登入")
    @PostMapping("/uiUserLogging")
    public ResponseEntity<LoginResponse> uiUserLogging(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = tokenService.createToken(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "換發refreshToken")
    @PostMapping("/uiGetRefreshToken")
    public ResponseEntity<Map<String,String>> uiUserLogging(@RequestBody Map<String,String> request){
        String refreshToken = request.get("refreshToken");
        Map<String,Object> accessToken = tokenService.refreshAccessToken(refreshToken);
        Map<String,String> res = Map.of("accessToken",accessToken.get("accessToken").toString());
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "驗證當前Token是否有效")
    @GetMapping("/uiCheckToken")
    public void uiCheckToken(){}
}
