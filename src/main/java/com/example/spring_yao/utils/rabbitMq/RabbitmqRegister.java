package com.example.spring_yao.utils.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqRegister {

    @Bean
    public Queue yao01A() { return new Queue(RabbitmqConstant.YAO_01_A); }

    @Bean
    public Queue yao01B() { return new Queue(RabbitmqConstant.YAO_01_B); }

    @Bean
    public Queue yao01C() { return new Queue(RabbitmqConstant.YAO_01_C); }

    @Bean
    public FanoutExchange yao01Exchange(){
        return new FanoutExchange(RabbitmqConstant.YAO_01_EXCHANGE);
    }

    @Bean
    public Binding yao01AFanoutExchange(){
        return BindingBuilder.bind(yao01A()).to(yao01Exchange());
    }

    @Bean
    public Binding yao01BFanoutExchange(){
        return BindingBuilder.bind(yao01B()).to(yao01Exchange());
    }

    @Bean
    public Binding yao01CFanoutExchange(){
        return BindingBuilder.bind(yao01C()).to(yao01Exchange());
    }

    //Direct範例
    @Bean
    public Queue directMq() {
        //綁訂死信交換機和路由
        Map<String,Object> args = new HashMap<>();
        //x-dead-letter-exchange: 當前業務綁定的死信交換機
        //消息被拒絕、消息過期、或者隊列達到最大長度，消息會變成死信
        args.put("x-dead-letter-exchange",RabbitmqConstant.DEAD_EXCHANGE);
        //x-dead-letter-routing-key: 當前業務的死信路由
        args.put("x-dead-letter-routing-key",RabbitmqConstant.DEAD_ROUTING);
        return new Queue("DirectMq", true, false, false, args);
    }

    //死信隊列
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(RabbitmqConstant.DEAD_ROUTING).build();
    }

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(RabbitmqConstant.DEAD_EXCHANGE);
    }

    @Bean
    public Binding deadQueueForDeadExchange(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(RabbitmqConstant.DEAD_ROUTING);
    }
}
