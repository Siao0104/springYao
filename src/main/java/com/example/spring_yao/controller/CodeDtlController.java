package com.example.spring_yao.controller;

import com.example.spring_yao.entity.CodeDtlEntity;
import com.example.spring_yao.model.codedtl.CodeDtlListVO;
import com.example.spring_yao.repository.CodeDtlRepository;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("springYao/codeDtl")
@Slf4j
@Tag(name = "代碼明細檔")
public class CodeDtlController {

    @Autowired
    private CodeDtlRepository codeDtlRepository;

    @Operation(summary = "取得全部的codeDtl")
    @GetMapping("/uiGetAllCodeDtl")
    public ResponseEntity<List<CodeDtlListVO>> uiGetAllCodeDtl(){
        List<CodeDtlEntity> codeDtlEntities = codeDtlRepository.findAll();
        List<CodeDtlListVO> codeDtlListVOS = JsonUtils.listTolist(codeDtlEntities,CodeDtlListVO.class);
        return ResponseEntity.ok(codeDtlListVOS);
    }

    @Operation(summary = "code dropDown內容")
    @GetMapping("/uiGetDtlCodeDesc/{code}")
    public ResponseEntity<List<CodeDtlListVO>> uiGetDtlCodeDesc(@PathVariable("code") String code){
        List<CodeDtlEntity> codeDtlEntities = codeDtlRepository.getDtlCodeDesc(code);
        List<CodeDtlListVO> codeDtlListVOS = JsonUtils.listTolist(codeDtlEntities,CodeDtlListVO.class);
        return ResponseEntity.ok(codeDtlListVOS);
    }
}
