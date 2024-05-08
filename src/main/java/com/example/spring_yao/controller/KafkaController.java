package com.example.spring_yao.controller;

import com.example.spring_yao.config.KafkaProducerConfig;
import com.example.spring_yao.model.userbasic.UserBasicListVO;
import com.example.spring_yao.repository.UserBasicRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("springYao/Kafka")
@Slf4j
@Tag(name = "Kafka發送")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, UserBasicListVO> userBasicListVOKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> strKafkaTemplate;

    @Autowired
    private UserBasicRepository userBasicRepository;

    @GetMapping
    public String testKafka(){
        UserBasicListVO userBasicListVO = new UserBasicListVO();
        userBasicListVO.setAccount("測試用帳號");
        userBasicListVO.setUserName("測試用姓名");
        userBasicListVOKafkaTemplate.send(KafkaProducerConfig.USER_TOPIC, userBasicListVO);

//        List<UserBasicEntity> userBasicEntities = userBasicRepository.findAll();
//        List<UserBasicListVO> userBasicListVOS = JsonUtils.listTolist(userBasicEntities,UserBasicListVO.class);
//        kafkaTemplate.send(KafkaProducerConfig.USERS_TOPIC,userBasicListVOS);

        strKafkaTemplate.send(KafkaProducerConfig.TEST_TOPIC, "Test kafka 發送");
        return "Published done";
    }

}
