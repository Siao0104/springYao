package com.example.spring_yao.config;

import com.example.spring_yao.model.userbasic.UserBasicListVO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    public static final String DEFAULT_SERVER = "localhost:9092";
    public static final String TEST_TOPIC = "test";
    public static final String USER_TOPIC = "user";
    public static final String USERS_TOPIC = "users";
    public static final String GROUP_1 = "group_1";
    public static final String GROUP_2 = "group_2";
    public static final String GROUP_3 = "group_3";

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_1);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserBasicListVO> userConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_2);
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        //設置反序列化錯誤處理
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        //設置錯誤處理反序列化
//        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//        //設置JsonDeserializer的trusted packages且信任所有類型
//        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
//        return new DefaultKafkaConsumerFactory<>(config);

        //處理反序列化錯誤問題
        ErrorHandlingDeserializer<UserBasicListVO> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(UserBasicListVO.class));
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserBasicListVO> userKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserBasicListVO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<UserBasicListVO>> usersConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_3);
        //處理反序列化錯誤問題
        ErrorHandlingDeserializer<List<UserBasicListVO>> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(List.class));
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<UserBasicListVO>> usersKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<UserBasicListVO>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(usersConsumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public CommonLoggingErrorHandler errorHandler(){
        return  new CommonLoggingErrorHandler();
    }
}
