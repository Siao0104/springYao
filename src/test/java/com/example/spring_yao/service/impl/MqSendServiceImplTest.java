package com.example.spring_yao.service.impl;

import com.example.spring_yao.service.MqSendService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MqSendServiceImplTest {

    @Autowired
    private MqSendService mqSendService;

    @BeforeAll
    public static void init(){
        System.out.println("單元測試啟動前!!");
    }

    @BeforeEach
    public void start(){
        System.out.println("開始測試...");
    }

    @Test
    public void sendA() {
        mqSendService.sendA();
    }

    @Test
    public void sendB() {
        mqSendService.sendB();
    }

    @Test
    public void sendC() {
        mqSendService.sendC();
    }

    @Test
    public void sendDirect() {
        mqSendService.sendDirect();
    }

    @AfterEach
    public void end(){
        System.out.println("測試結束...");
    }

    @AfterAll
    public static void close(){
        System.out.println("單元測試結束後!!");
    }

    @Disabled("此注釋的方法不執行測試")
    @Test
    public void some(){
        System.out.println("TEST");
    }

    @Disabled("此注釋的方法不執行測試")
    @DisplayName("測試專用")
    @ValueSource(strings = {"AAA","BBB","CCC"})
    @ParameterizedTest(name = "傳入參數 = {1}")
    public void All(String name){
        Assertions.assertEquals(name,"BBB");
    }
}