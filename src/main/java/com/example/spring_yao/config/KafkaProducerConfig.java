package com.example.spring_yao.config;

import com.example.spring_yao.model.userbasic.UserBasicListVO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    public static final String DEFAULT_SERVER = "192.168.0.10:9092";
    public static final String TEST_TOPIC = "test";
    public static final String USER_TOPIC = "user";
    public static final String USERS_TOPIC = "users";
//    public static final String DEAD_LETTER_USER_TOPIC = "dead_letter_user_topic";
//    public static final String DEAD_LETTER_STR_TOPIC = "dead_letter_str_topic";

    //For UserBasicListVO物件
    @Bean
    public ProducerFactory<String, UserBasicListVO> userBasicListVOProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
        //key值序列化
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //value值序列化
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, UserBasicListVO> userBasicListVOKafkaTemplate() {
        return new KafkaTemplate<>(userBasicListVOProducerFactory());
    }

    @Bean
    public KafkaTemplate<String,UserBasicListVO> retryableUserBasicListVOTemplate(){
        return new KafkaTemplate<>(userBasicListVOProducerFactory());
    }

    //For String字串
    @Bean
    public ProducerFactory<String, String> strProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
        //key值序列化
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //value值序列化
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> strKafkaTemplate() {
        return new KafkaTemplate<>(strProducerFactory());
    }

    @Bean
    public KafkaTemplate<String,String> retryableStrTemplate(){
        return new KafkaTemplate<>(strProducerFactory());
    }

    @Bean
    public NewTopic defaultTopic() {
        return TopicBuilder.name(USER_TOPIC).build();
    }

//    @Bean
//    public NewTopic deadLetterTopicStr(){
//        return TopicBuilder.name(DEAD_LETTER_STR_TOPIC).build();
//    }
//
//    @Bean
//    public DeadLetterPublishingRecoverer deadLetterPublishingRecovererForStr(KafkaTemplate<Object, Object> kafkaTemplate) {
//        return new DeadLetterPublishingRecoverer(kafkaTemplate, (r, e) -> new TopicPartitionOffset(DEAD_LETTER_STR_TOPIC, r.partition(), r.offset()).getTopicPartition());
//    }
//
//    @Bean
//    public NewTopic deadLetterTopicUser(){
//        return TopicBuilder.name(DEAD_LETTER_USER_TOPIC).build();
//    }
//
//    @Bean
//    public DeadLetterPublishingRecoverer deadLetterPublishingRecovererForUser(KafkaTemplate<Object, Object> kafkaTemplate) {
//        return new DeadLetterPublishingRecoverer(kafkaTemplate, (r, e) -> new TopicPartitionOffset(DEAD_LETTER_USER_TOPIC, r.partition(), r.offset()).getTopicPartition());
//    }
}
