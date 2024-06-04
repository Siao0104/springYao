package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.UserCalendarEntity;
import com.example.spring_yao.repository.UserCalendarRepository;
import com.example.spring_yao.service.UserCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserCalendarServiceImpl implements UserCalendarService {

    @Autowired
    private UserCalendarRepository userCalendarRepository;

    /**
     * 檢查當天代辦事項是否超過3筆
     * @author Yao
     * @param userCalendarEntity
     * Last Modify author Yao Date: 2024/6/4 Ver:1.0
     * change Description
     * */
    public int checkThatDayCalendar(UserCalendarEntity userCalendarEntity) throws Exception{
        try{
            List<UserCalendarEntity> userCalendarEntities = userCalendarRepository.findAllByExecuteDayAndUserName(userCalendarEntity.getExecuteDay(), userCalendarEntity.getUserName());
            return userCalendarEntities.size();
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception();
        }
    }
}
