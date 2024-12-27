package com.example.spring_yao.controller;

import com.example.spring_yao.dto.VotingDTO;
import com.example.spring_yao.dto.VotingTotalDTO;
import com.example.spring_yao.entity.VotingRecordsEntity;
import com.example.spring_yao.model.votingrecords.VotingRecordsCrForm;
import com.example.spring_yao.model.votingrecords.VotingRecordsListVO;
import com.example.spring_yao.repository.VotingRecordsRepository;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("springYao/votingRecords")
@Slf4j
@Tag(name = "投票紀錄")
public class VotingRecordsController {

    @Autowired
    private VotingRecordsRepository votingRecordsRepository;

    @Operation(summary = "取得個人的的投票紀錄")
    @GetMapping("/uiGetPersonalVotingRecords/{voterName}")
    public ResponseEntity<List<VotingRecordsListVO>> uiGetPersonalVotingRecords(@PathVariable("voterName") String voterName) {
        List<VotingDTO> votingDTOS = votingRecordsRepository.findByVoterNameWithItemName(voterName);
        List<VotingRecordsListVO> votingRecordsListVOS = JsonUtils.listTolist(votingDTOS,VotingRecordsListVO.class);
        votingRecordsListVOS.forEach(vo -> vo.setRowStatus("R"));
        return new ResponseEntity<>(votingRecordsListVOS, HttpStatus.OK);
    }

    @Operation(summary = "修改投票紀錄")
    @PostMapping("/uiSaveVotingRecords")
    public ResponseEntity<String> uiSaveVotingRecords(@RequestBody List<VotingRecordsCrForm> votingRecordsCrForms){
        try{
            List<VotingRecordsEntity> votingRecordsEntities = JsonUtils.listTolist(votingRecordsCrForms, VotingRecordsEntity.class);
            for(VotingRecordsEntity votingRecordsEntity : votingRecordsEntities) {
                if("D".equals(votingRecordsEntity.getRowStatus())){
                    votingRecordsRepository.deleteById(votingRecordsEntity.getId());
                }else if("C".equals(votingRecordsEntity.getRowStatus())){
                    votingRecordsRepository.saveAll(votingRecordsEntities);
                }
            }
            return new ResponseEntity<>("修改成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return new ResponseEntity<>("修改失敗", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "刪除投票內容時檢核是否有投票紀錄")
    @GetMapping("/uiCheckVotingRecords/{itemCode}")
    public ResponseEntity<Boolean> uiCheckVotingRecords(@PathVariable("itemCode") String itemCode){
        List<VotingRecordsEntity> votingRecordsEntities = votingRecordsRepository.findByItemCode(itemCode);
        if(votingRecordsEntities.isEmpty()){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @Operation(summary = "取得總投票結果")
    @GetMapping("/uiGetTotalVotingResult")
    public ResponseEntity<List<VotingTotalDTO>> uiGetTotalVotingResult(){
        List<VotingTotalDTO> votingTotalDTOS = votingRecordsRepository.countByItemCodeTotalVotes();
        return new ResponseEntity<>(votingTotalDTOS, HttpStatus.OK);
    }
}
