package com.example.spring_yao.repository;

import com.example.spring_yao.entity.CodeMstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeMstRepository extends JpaRepository<CodeMstEntity,Integer>, JpaSpecificationExecutor<CodeMstEntity> {

    CodeMstEntity getByCode(String code);
}
