package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.CodeMstEntity;
import com.example.spring_yao.repository.CodeMstRepository;
import com.example.spring_yao.service.CodeMstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CodeMstServiceImpl implements CodeMstService {

    @Autowired
    private CodeMstRepository codeMstRepository;

    /**
     * 刪除代碼細項
     * @author Yao
     * @param id,codeMstId
     * Last Modify author Yao Date: 2024/9/20 Ver:1.0
     * change Description
     * */
    public Boolean brDeleteCodeDtl(int id, int codeMstId) throws Exception{
        try{
            Optional<CodeMstEntity> mstOptional = codeMstRepository.findById(codeMstId);
            if(mstOptional.isPresent()){
                CodeMstEntity mstEntity = mstOptional.get();
                //找出要刪除的子項
                mstEntity.getCodeDtlEntities().removeIf(dtl->dtl.getId() == id);
                //儲存父項
                codeMstRepository.save(mstEntity);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new Exception(e.getMessage());
        }
    }
}
