package com.example.spring_yao.controller;

import com.example.spring_yao.entity.CodeDtlEntity;
import com.example.spring_yao.model.codedtl.CodeDtlListVO;
import com.example.spring_yao.model.codedtl.CodeDtlPagination;
import com.example.spring_yao.repository.CodeDtlRepository;
import com.example.spring_yao.service.CodeDtlService;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("springYao/codeDtl")
@Slf4j
@Tag(name = "代碼明細檔")
public class CodeDtlController {

    @Autowired
    private CodeDtlRepository codeDtlRepository;

    @Autowired
    private CodeDtlService codeDtlService;

    @Operation(summary = "取得全部的codeDtl")
    @GetMapping("/uiGetAllCodeDtl")
    public ResponseEntity<List<CodeDtlListVO>> uiGetAllCodeDtl(){
        List<CodeDtlEntity> codeDtlEntities = codeDtlRepository.findAll();
        List<CodeDtlListVO> codeDtlListVOS = JsonUtils.listTolist(codeDtlEntities,CodeDtlListVO.class);
        return ResponseEntity.ok(codeDtlListVOS);
    }

    @Operation(summary = "取得含有pageable的codeDtl")
    @PostMapping("/uiGetAllCodeDtlPageable")
    public ResponseEntity<Map<String, Object>> uiGetAllCodeDtlPageable(@RequestBody CodeDtlPagination codeDtlPagination) {
        Pageable pageable = PageRequest.of(codeDtlPagination.getPage()-1, codeDtlPagination.getSize());
        Specification<CodeDtlEntity> specification = (root, query, criteriaBuilder) -> {
            // codeMstId = ???
            return criteriaBuilder.equal(root.get("codeMstEntity").get("id"), codeDtlPagination.getCodeMstId());
        };
        Page<CodeDtlEntity> pageResult = codeDtlRepository.findAll(specification, pageable);
        List<CodeDtlListVO> codeDtlListVOS = JsonUtils.listTolist(pageResult.getContent(), CodeDtlListVO.class);
        for(CodeDtlListVO codeDtlListVO : codeDtlListVOS){
            codeDtlListVO.setCodeMstId(pageResult.getContent().get(0).getCodeMstEntity().getId());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", codeDtlListVOS);
        response.put("totalItems", pageResult.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "code dropDown內容")
    @GetMapping("/uiGetDtlCodeDesc/{code}")
    public ResponseEntity<List<CodeDtlListVO>> uiGetDtlCodeDesc(@PathVariable("code") String code){
        List<CodeDtlEntity> codeDtlEntities = codeDtlRepository.getDtlCodeDesc(code);
        List<CodeDtlListVO> codeDtlListVOS = JsonUtils.listTolist(codeDtlEntities,CodeDtlListVO.class);
        return ResponseEntity.ok(codeDtlListVOS);
    }
}
