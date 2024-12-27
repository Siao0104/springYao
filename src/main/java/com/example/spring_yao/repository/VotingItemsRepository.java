package com.example.spring_yao.repository;

import com.example.spring_yao.entity.VotingItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VotingItemsRepository extends JpaRepository<VotingItemsEntity,Integer>, JpaSpecificationExecutor<VotingItemsEntity> {

    VotingItemsEntity findById(int id);
}
