package com.example.spring_yao.utils.RabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
