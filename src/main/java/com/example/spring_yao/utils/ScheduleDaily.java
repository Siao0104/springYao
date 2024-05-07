package com.example.spring_yao.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleDaily {

    private static final SimpleDateFormat sdfHms = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0 0/1 * * ?")
    private void timingJob(){
        System.out.println(STR."現在時間 : \{sdfHms.format(new Date())}");
    }
}