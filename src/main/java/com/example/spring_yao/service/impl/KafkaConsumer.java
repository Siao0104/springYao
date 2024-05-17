package com.example.spring_yao.service.impl;

import com.example.spring_yao.config.KafkaConsumerConfig;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {

    @RetryableTopic(attempts = "5",kafkaTemplate = "retryableStrTemplate")
    @KafkaListener(topics = KafkaConsumerConfig.TEST_TOPIC,groupId = KafkaConsumerConfig.GROUP_1)
    public void consume(String message) throws Exception{
        System.out.println("測試STR");
        throw new Exception("測試是否重新發送String");
//        System.out.println(STR."Message : \{message}");
    }

    @RetryableTopic(attempts = "2",kafkaTemplate = "retryableUserBasicListVOTemplate")
    @KafkaListener(topics = KafkaConsumerConfig.USER_TOPIC, groupId = KafkaConsumerConfig.GROUP_2,containerFactory = "userKafkaListenerFactory")
    public void consumeJson(UserBasicListVO userBasicListVO) throws Exception{
        System.out.println("測試UserBasicListVO");
        throw new Exception("測試是否重新發送UserBasicListVO");
//        System.out.println(STR."UserListVO : \{userBasicListVO}");
    }

    @KafkaListener(topics = KafkaConsumerConfig.USERS_TOPIC, groupId = KafkaConsumerConfig.GROUP_3,containerFactory = "usersKafkaListenerFactory")
    public void consumeJson(List<UserBasicListVO> userBasicListVOS){
        System.out.println(String.format("UserListVOS : {}",userBasicListVOS));
    }

    @DltHandler
    public <T> void dltHandler(T object){
        System.out.println(String.format("死信隊列處理結果 : {}",object));
    }
}
