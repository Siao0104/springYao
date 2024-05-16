package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.TimeTaskEntity;
import com.example.spring_yao.repository.TimeTaskRepository;
import com.example.spring_yao.service.TimeTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TimeTaskServiceImpl implements TimeTaskService {

    @Autowired
    private TimeTaskRepository timeTaskRepository;

    /**
     * 檢核是否有重複的排程方法
     * @author Yao
     * @param timeTaskEntity
     * Last Modify author Yao Date: 2024/5/9 Ver:1.0
     * change Description
     * */
    public int checkTimeTaskName(TimeTaskEntity timeTaskEntity) throws Exception {
        try{
            List<TimeTaskEntity> timeTaskEntities = timeTaskRepository.findAllByTaskName(timeTaskEntity.getTaskName());
            return timeTaskEntities.size();
        }catch (Exception e){
            log.error(STR."錯誤 : \{e.getMessage()}");
            throw new Exception();
        }
    }
}
