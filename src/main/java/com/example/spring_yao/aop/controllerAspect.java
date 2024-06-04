package com.example.spring_yao.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

@Component
@Aspect
@Slf4j
public class controllerAspect {

    private long startTime;

    private String className;

    private final String POINT_CUT = "execution (* com.*.*.controller.*.ui*(..))";

    @Pointcut(POINT_CUT)
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        startTime = System.nanoTime();
        //取得方法參數
        String className = joinPoint.getSignature().getName();

        Optional.ofNullable(className)
                .ifPresent(this::processArguments);
    }

    private void processArguments(String name) {className = name;}

    @AfterReturning("pointcut()")
    public  void afterReturning(){
        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime)/1_000_000_000.0;
        DecimalFormat df = new DecimalFormat("#.##");
        log.info(String.format("方法名稱 : %s ，方法總執行時間 : %s 秒",className,df.format(durationSeconds)));
    }

}
