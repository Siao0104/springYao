package com.example.spring_yao.controller;

import com.example.spring_yao.entity.VotingItemsEntity;
import com.example.spring_yao.entity.VotingRecordsEntity;
import com.example.spring_yao.model.votingItems.VotingItemsCrForm;
import com.example.spring_yao.model.votingItems.VotingItemsListVO;
import com.example.spring_yao.model.votingItems.VotingItemsPagination;
import com.example.spring_yao.repository.VotingItemsRepository;
import com.example.spring_yao.repository.VotingRecordsRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("springYao/votingItems")
@Slf4j
@Tag(name = "投票項目")
public class VotingItemsController {

    @Autowired
    private VotingItemsRepository votingItemsRepository;

    @Autowired
    private VotingRecordsRepository votingRecordsRepository;

    @Operation(summary = "取得全部的投票項目")
    @GetMapping("/uiGetAllVotingItems")
    public ResponseEntity<List<VotingItemsListVO>> uiGetAllVotingItems() {
        List<VotingItemsEntity> votingItemsEntities = votingItemsRepository.findAll();
        List<VotingItemsListVO> votingItemsListVOS = JsonUtils.listTolist(votingItemsEntities,VotingItemsListVO.class);
        return new ResponseEntity<>(votingItemsListVOS, HttpStatus.OK);
    }

    @Operation(summary = "取得含有pageable的投票項目")
    @PostMapping("/uiGetAllVotingItemsPageable")
    public ResponseEntity<Map<String, Object>> uiGetAllVotingItemsPageable(@RequestBody VotingItemsPagination votingItemsPagination) {
        Pageable pageable = PageRequest.of(votingItemsPagination.getPage()-1, votingItemsPagination.getSize());

        Specification<VotingItemsEntity> specification = (root, query, criteriaBuilder) -> {
            String searchValue = votingItemsPagination.getDataType();
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("itemCode"), "%" + searchValue + "%"),
                    criteriaBuilder.like(root.get("itemName"), "%" + searchValue + "%")
            );
        };

        Page<VotingItemsEntity> pageResult = votingItemsRepository.findAll(specification,pageable);
        List<VotingItemsListVO> votingItemsListVOS = JsonUtils.listTolist(pageResult.getContent(), VotingItemsListVO.class);
        Map<String, Object> response = new HashMap<>();
        response.put("data", votingItemsListVOS);
        response.put("totalItems", pageResult.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "新增/修改投票項目")
    @PostMapping("/uiSaveVotingItems")
    public ResponseEntity<String> uiSaveVotingItems(@RequestBody VotingItemsCrForm votingItemsCrForm){
        try{
            VotingItemsEntity votingItemsEntity = JsonUtils.objectToObject(votingItemsCrForm, VotingItemsEntity.class);
            votingItemsRepository.save(votingItemsEntity);
            return new ResponseEntity<>("投票項目保存成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            return new ResponseEntity<>("投票項目保存失敗", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "刪除投票項目")
    @DeleteMapping("/uiDeleteVotingItems/{id}")
    @Transactional
    public ResponseEntity<String> uiDeleteVotingItems(@PathVariable("id") int id) throws Exception {
        try{
            VotingItemsEntity votingItemsEntity = votingItemsRepository.findById(id);
            List<VotingRecordsEntity> votingRecordsEntities = votingRecordsRepository.findByItemCode(votingItemsEntity.getItemCode());
            votingRecordsRepository.deleteAllInBatch(votingRecordsEntities);
            votingItemsRepository.deleteById(id);
            return new ResponseEntity<>("投票項目刪除成功", HttpStatus.OK);
        }catch (Exception e){
            log.error(String.format("錯誤 : %s",e.getMessage()));
            throw new RuntimeException("刪除失敗", e);
        }
    }
}
