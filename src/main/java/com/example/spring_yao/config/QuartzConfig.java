package com.example.spring_yao.config;

import com.example.spring_yao.utils.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail(){
        //綁定具體工作
        //storeDurably 持久化
        return JobBuilder.newJob(QuartzJob.class).storeDurably().build();
    }

    @Bean
    public Trigger JobTrigger(){
        ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder.cronSchedule("0 * * * * ?");
        //綁定對應工作明細
        return TriggerBuilder.newTrigger().forJob(jobDetail()).withSchedule(scheduleBuilder).build();
    }
}
