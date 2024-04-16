package com.example.spring_yao.repository;

import com.example.spring_yao.entity.CodeDtlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeDtlRepository extends JpaRepository<CodeDtlEntity,Integer> {

    @Query(value = "select dtl.* " +
            "from code_mst mst,code_dtl dtl " +
            "where mst.id = dtl.code_mst_id " +
            "and dtl.enabled ='Y' " +
            "and mst.code = (:code)",nativeQuery = true)
    List<CodeDtlEntity> getDtlCodeDesc(@Param("code") String code);
}
