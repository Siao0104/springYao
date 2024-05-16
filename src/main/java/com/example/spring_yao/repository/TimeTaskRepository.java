package com.example.spring_yao.repository;

import com.example.spring_yao.entity.TimeTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTaskRepository extends JpaRepository<TimeTaskEntity,Integer> {
    TimeTaskEntity findByTaskName(String taskName);
    List<TimeTaskEntity> findAllByTaskName(String taskName);
}
