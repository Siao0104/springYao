package com.example.spring_yao.controller;

import com.example.spring_yao.service.MqSendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("springYao/Mq")
@Slf4j
@Tag(name = "Mq發送")
public class MqController {

    @Autowired
    private MqSendService mqSendService;

    @GetMapping
    public void testMq(){
        mqSendService.sendA();
        mqSendService.sendB();
        mqSendService.sendC();
        mqSendService.sendDirect();
    }
}
