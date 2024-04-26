package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import com.example.spring_yao.repository.UserBasicRepository;
import com.example.spring_yao.service.MqSendService;
import com.example.spring_yao.utils.JsonUtils;
import com.example.spring_yao.utils.RabbitMq.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MqSendServiceImpl implements MqSendService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserBasicRepository userBasicRepository;

    public void sendA(){
        String a = "AAAAAAAAAAAA";
        rabbitTemplate.convertAndSend(RabbitmqConstant.YAO_01_A,a);
    }

    public void sendB(){
        UserBasicEntity userBasicEntitiy = userBasicRepository.getByAccount("yao");
        rabbitTemplate.convertAndSend(RabbitmqConstant.YAO_01_B,userBasicEntitiy);
    }

    public void sendC(){
        List<UserBasicEntity> userBasicEntities = userBasicRepository.findAll();
        List<UserBasicListVO> userBasicListVOS = JsonUtils.listTolist(userBasicEntities,UserBasicListVO.class);
        rabbitTemplate.convertAndSend(RabbitmqConstant.YAO_01_C,userBasicListVOS);
    }

}
