package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.CodeDtlEntity;
import com.example.spring_yao.entity.CodeMstEntity;
import com.example.spring_yao.repository.CodeMstRepository;
import com.example.spring_yao.service.CodeMstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CodeMstServiceImpl implements CodeMstService {

    @Autowired
    private CodeMstRepository codeMstRepository;
    public void brPutNewCodeMst() throws Exception{
        try{
            CodeMstEntity codeMstEntity = new CodeMstEntity()
                    .setCode("country")
                    .setCodeDesc("國籍")
                    .setEnabled("Y");
            CodeDtlEntity codeDtlEntity = new CodeDtlEntity()
                    .setCode("TW")
                    .setCodeDesc("台灣")
                    .setEnabled("Y")
                    .setCodeMstEntity(codeMstEntity);
            CodeDtlEntity codeDtlEntity1 = new CodeDtlEntity()
                    .setCode("HK")
                    .setCodeDesc("香港")
                    .setEnabled("Y")
                    .setCodeMstEntity(codeMstEntity);
            List<CodeDtlEntity> codeDtlEntities = new ArrayList<>();
            codeDtlEntities.add(codeDtlEntity);
            codeDtlEntities.add(codeDtlEntity1);
            codeMstEntity.setCodeDtlEntities(codeDtlEntities);
            codeMstRepository.save(codeMstEntity);
        }catch (Exception e){
            log.error(STR."錯誤 : \{e.getMessage()}");
            throw new Exception();
        }
    }
}
