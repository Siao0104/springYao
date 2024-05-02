package com.example.spring_yao.service.impl;

import com.example.spring_yao.entity.CodeDtlEntity;
import com.example.spring_yao.entity.CodeMstEntity;
import com.example.spring_yao.repository.CodeMstRepository;
import com.example.spring_yao.service.CodeMstService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class CodeMstServiceImplTest {

    @Autowired
    private CodeMstService codeMstService;

    @MockBean
    private CodeMstRepository codeMstRepository;

    private CodeMstEntity codeMstEntity;

    @BeforeEach
    void setUp() {
        // 初始化測試數據
        codeMstEntity = new CodeMstEntity()
                .setCode("country")
                .setCodeDesc("國籍")
                .setEnabled("Y");

        CodeDtlEntity codeDtlEntity1 = new CodeDtlEntity()
                .setCode("TW")
                .setCodeDesc("台灣")
                .setEnabled("Y")
                .setCodeMstEntity(codeMstEntity);

        CodeDtlEntity codeDtlEntity2 = new CodeDtlEntity()
                .setCode("HK")
                .setCodeDesc("香港")
                .setEnabled("Y")
                .setCodeMstEntity(codeMstEntity);

        List<CodeDtlEntity> codeDtlEntities = new ArrayList<>();
        codeDtlEntities.add(codeDtlEntity1);
        codeDtlEntities.add(codeDtlEntity2);

        codeMstEntity.setCodeDtlEntities(codeDtlEntities);
    }

    @Test
    void brPutNewCodeMst() throws Exception {
        // 設定 Mock 行為
        when(codeMstRepository.save(any())).thenAnswer(invocation -> {
            CodeMstEntity savedEntity = invocation.getArgument(0);
            System.out.println(STR."Saving entity: \{savedEntity}"); // 打印參數到 log
            return savedEntity; // 返回儲存的實體
        });

        // 執行測試方法
        codeMstService.brPutNewCodeMst();

        // 驗證儲存行為是否發生
        verify(codeMstRepository, times(1)).save(any());

        // 捕獲 save() 方法的參數
        ArgumentCaptor<CodeMstEntity> captor = ArgumentCaptor.forClass(CodeMstEntity.class);
        verify(codeMstRepository).save(captor.capture());

        // 從捕獲的參數中獲取實體
        CodeMstEntity savedEntity = captor.getValue();
        assertEquals("country",savedEntity.getCode());
        // 打印參數到 log
        System.out.println(STR."Saved entity: \{savedEntity}");
    }
}