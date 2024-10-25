package com.example.spring_yao.repository;

import com.example.spring_yao.entity.UserBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBasicRepository extends JpaRepository<UserBasicEntity,Integer> {
    UserBasicEntity getByAccount(String account);

    UserBasicEntity getByEmail(String email);

    List<UserBasicEntity> getAllByAccount(String account);

    List<UserBasicEntity> getAllByEmail(String email);
}
