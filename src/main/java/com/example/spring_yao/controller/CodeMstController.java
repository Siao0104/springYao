package com.example.spring_yao.controller;

import com.example.spring_yao.entity.CodeMstEntity;
import com.example.spring_yao.model.codemst.CodeMstListVO;
import com.example.spring_yao.repository.CodeMstRepository;
import com.example.spring_yao.service.CodeMstService;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "測試dropDown新增")
    @PutMapping("/uiPutNewCodeMst")
    public void uiPutNewCodeMst() throws Exception {
        codeMstService.brPutNewCodeMst();
    }
}
