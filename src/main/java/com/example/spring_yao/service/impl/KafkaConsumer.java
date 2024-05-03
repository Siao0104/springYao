package com.example.spring_yao.service.impl;

import com.example.spring_yao.config.KafkaConsumerConfig;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = KafkaConsumerConfig.TEST_TOPIC,groupId = KafkaConsumerConfig.GROUP_1)
    public void consume(String message){
        System.out.println(STR."Message : \{message}");
    }

    @KafkaListener(topics = KafkaConsumerConfig.USER_TOPIC, groupId = KafkaConsumerConfig.GROUP_2,containerFactory = "userKafkaListenerFactory")
    public void consumeJson(UserBasicListVO userBasicListVO){
        System.out.println(STR."UserListVO : \{userBasicListVO}");
    }

    @KafkaListener(topics = KafkaConsumerConfig.USERS_TOPIC, groupId = KafkaConsumerConfig.GROUP_3,containerFactory = "usersKafkaListenerFactory")
    public void consumeJson(List<UserBasicListVO> userBasicListVOS){
        System.out.println(STR."UserListVOS : \{userBasicListVOS}");
    }
}
