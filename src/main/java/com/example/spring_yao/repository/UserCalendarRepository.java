package com.example.spring_yao.repository;

import com.example.spring_yao.entity.UserCalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserCalendarRepository extends JpaRepository<UserCalendarEntity,Integer> {

    List<UserCalendarEntity> findAllByUserNameOrderByExecuteDayDesc(String userName);

    List<UserCalendarEntity> findAllByExecuteDayAndUserName(Date executeDay,String userName);
}
