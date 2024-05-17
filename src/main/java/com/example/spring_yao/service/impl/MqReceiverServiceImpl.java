package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import com.example.spring_yao.utils.rabbitMq.RabbitmqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MqReceiverServiceImpl {

    @RabbitListener(queues = RabbitmqConstant.YAO_01_A)
    @RabbitHandler
    public void receiverA(String a){
        System.out.println(String.format("A : {}",a));
    }

    @RabbitListener(queues = RabbitmqConstant.YAO_01_B)
    @RabbitHandler
    public void receiverB(UserBasicEntity userBasicEntitiy){
        System.out.println(String.format("userBasicEntitiy : {}",userBasicEntitiy));
    }

    @RabbitListener(queues = RabbitmqConstant.YAO_01_C)
    @RabbitHandler
    public void receiverC(List<UserBasicListVO> userBasicListVOS){
        System.out.println(String.format("userBasicListVOS : {}",userBasicListVOS));
    }

    @RabbitListener(queues = "DirectMq")
    @RabbitHandler
    public void receiverDirect(String direct) throws Exception{
        throw new Exception("模擬接收失敗");
//        System.out.println(String.format("DirectMq 測試: {}",direct));
    }
}
