package com.example.spring_yao.controller;

import com.example.spring_yao.config.KafkaProducerConfig;
import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import com.example.spring_yao.repository.UserBasicRepository;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("springYao/Kafka")
@Slf4j
@Tag(name = "Kafka發送")
public class KafkaController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private UserBasicRepository userBasicRepository;

    @GetMapping
    public String testKafka(){
        UserBasicListVO userBasicListVO = new UserBasicListVO();
        userBasicListVO.setAccount("測試用帳號");
        userBasicListVO.setUserName("測試用姓名");

        kafkaTemplate.send(KafkaProducerConfig.USER_TOPIC, userBasicListVO);

        List<UserBasicEntity> userBasicEntities = userBasicRepository.findAll();
        List<UserBasicListVO> userBasicListVOS = JsonUtils.listTolist(userBasicEntities,UserBasicListVO.class);
        kafkaTemplate.send(KafkaProducerConfig.USERS_TOPIC,userBasicListVOS);

        kafkaTemplate.send(KafkaProducerConfig.TEST_TOPIC, "Test kafka 發送");
        return "Published done";
    }

}
