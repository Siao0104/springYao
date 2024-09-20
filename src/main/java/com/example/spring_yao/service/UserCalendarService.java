package com.example.spring_yao.service;

import com.example.spring_yao.entity.UserCalendarEntity;

public interface UserCalendarService {

    /**
     * 檢查當天代辦事項是否超過3筆
     * @author Yao
     * @param userCalendarEntity
     * Last Modify author Yao Date: 2024/6/4 Ver:1.0
     * change Description
     * */
    int checkThatDayCalendar(UserCalendarEntity userCalendarEntity) throws Exception;
}
