package com.example.spring_yao.controller;

import com.example.spring_yao.entity.CodeMstEntity;
import com.example.spring_yao.model.codemst.CodeMstListVO;
import com.example.spring_yao.model.codemst.CodeMstPagination;
import com.example.spring_yao.repository.CodeMstRepository;
import com.example.spring_yao.service.CodeMstService;
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
@RequestMapping("springYao/codeMst")
@Slf4j
@Tag(name = "代碼明細主檔")
public class CodeMstController {

    @Autowired
    private CodeMstRepository codeMstRepository;

    @Autowired
    private CodeMstService codeMstService;

    @Operation(summary = "取得全部的codeMst")
    @GetMapping("/uiGetAllCodeMst")
    public ResponseEntity<List<CodeMstListVO>> uiGetAllCodeMst() {
        List<CodeMstEntity> codeMstEntities = codeMstRepository.findAll();
        List<CodeMstListVO> codeMstListVOS = JsonUtils.listTolist(codeMstEntities,CodeMstListVO.class);
        return new ResponseEntity<>(codeMstListVOS, HttpStatus.OK);
    }

    @Operation(summary = "取得含有pageable的codeMst")
    @PostMapping("/uiGetAllCodeMstPageable")
    public ResponseEntity<Map<String, Object>> uiGetAllCodeMstPageable(@RequestBody CodeMstPagination codeMstPagination) {
        Pageable pageable = PageRequest.of(codeMstPagination.getPage()-1, codeMstPagination.getSize());

        Specification<CodeMstEntity> specification = (root, query, criteriaBuilder) -> {
            String searchValue = codeMstPagination.getDataType();
            // code like '%searchValue%' or enabled like '%searchValue%'
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("code"), "%" + searchValue + "%"),
                    criteriaBuilder.like(root.get("enabled"), "%" + searchValue + "%")
            );
        };

        Page<CodeMstEntity> pageResult = codeMstRepository.findAll(specification,pageable);
        List<CodeMstListVO> codeMstListVOS = JsonUtils.listTolist(pageResult.getContent(), CodeMstListVO.class);
        Map<String, Object> response = new HashMap<>();
        response.put("data", codeMstListVOS);
        response.put("totalItems", pageResult.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "新增/修改代碼主檔")
    @PostMapping("/uiSaveCodeMst")
    public ResponseEntity<String> uiAddCodeMst(@RequestBody List<CodeMstEntity> codeMstEntities){
        try{
            codeMstRepository.saveAll(codeMstEntities);
            return new ResponseEntity<>("代碼主檔保存成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return new ResponseEntity<>("代碼主檔保存失敗", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "刪除代碼主檔")
    @DeleteMapping("/uiDeleteCodeMst/{id}")
    public ResponseEntity<String> uiDeleteCodeMst(@PathVariable("id") int id) throws Exception {
        try{
            codeMstRepository.deleteById(id);
            return new ResponseEntity<>("代碼主檔刪除成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return new ResponseEntity<>("代碼主檔刪除失敗", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "刪除代碼明細")
    @DeleteMapping("/uiDeleteCodeDtl/{id}/{codeMstId}")
    public ResponseEntity<String> uiDeleteCodeDtl(@PathVariable("id") int id,@PathVariable("codeMstId") int codeMstId) throws Exception {
        if(codeMstService.brDeleteCodeDtl(id,codeMstId)){
            return new ResponseEntity<>("代碼明細刪除成功", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("代碼明細刪除失敗", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
