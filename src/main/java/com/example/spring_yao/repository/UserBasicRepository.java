package com.example.spring_yao.repository;

import com.example.spring_yao.entity.UserBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicRepository extends JpaRepository<UserBasicEntity,Integer> {
    UserBasicEntity getByAccount(String account);
}
