package com.example.spring_yao.controller;

import com.example.spring_yao.entity.TimeTaskEntity;
import com.example.spring_yao.model.timetask.TimeTaskCrForm;
import com.example.spring_yao.model.timetask.TimeTaskListVO;
import com.example.spring_yao.model.timetask.TimeTaskUpForm;
import com.example.spring_yao.repository.TimeTaskRepository;
import com.example.spring_yao.service.TimeTaskService;
import com.example.spring_yao.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("springYao/timeTask")
@Slf4j
@Tag(name = "排程設定")
public class TimeTaskController {

    @Autowired
    private TimeTaskRepository timeTaskRepository;

    @Autowired
    private TimeTaskService timeTaskService;

    @Operation(summary = "取得所有排程")
    @GetMapping("/uiGetAllTimeTask")
    public ResponseEntity<List<TimeTaskListVO>> uiGetAllTimeTask(){
        List<TimeTaskEntity> timeTaskEntities = timeTaskRepository.findAll();
        List<TimeTaskListVO> timeTaskListVOS = JsonUtils.listTolist(timeTaskEntities,TimeTaskListVO.class);
        return ResponseEntity.ok(timeTaskListVOS);
    }

    @Operation(summary = "透過排程方法，取得排程")
    @GetMapping("/uiFindByTaskName/{taskName}")
    public ResponseEntity uiFindByTaskName(@PathVariable("taskName") String taskName){
        TimeTaskEntity timeTaskEntity = timeTaskRepository.findByTaskName(taskName);
        if(timeTaskEntity==null){
            return new ResponseEntity<>("查無該排程", HttpStatus.BAD_REQUEST);
        }else{
            TimeTaskListVO timeTaskListVO = JsonUtils.objectToObject(timeTaskEntity,TimeTaskListVO.class);
            return new ResponseEntity<>(timeTaskListVO, HttpStatus.OK);
        }
    }

    @Operation(summary = "新增排程")
    @PostMapping("/uiSetNewTimeTask")
    public ResponseEntity<String> uiSetNewTimeTask(@RequestBody @Valid TimeTaskCrForm timeTaskCrForm) throws Exception{
        TimeTaskEntity timeTaskEntity = JsonUtils.objectToObject(timeTaskCrForm,TimeTaskEntity.class);
        if(timeTaskService.checkTimeTaskName(timeTaskEntity)>1){
            return new ResponseEntity<>("排程名稱重複", HttpStatus.BAD_REQUEST);
        }else{
            timeTaskRepository.save(timeTaskEntity);
            return new ResponseEntity<>("新增排程成功", HttpStatus.OK);
        }
    }

    @Operation(summary = "更改排程")
    @PutMapping("/uiUpdateTimeTask")
    public ResponseEntity<String> uiUpdateTimeTask(@RequestBody @Valid TimeTaskUpForm timeTaskUpForm) throws Exception{
        TimeTaskEntity timeTaskEntity = JsonUtils.objectToObject(timeTaskUpForm,TimeTaskEntity.class);
        if(timeTaskService.checkTimeTaskName(timeTaskEntity)>0){
            return new ResponseEntity<>("排程名稱重複", HttpStatus.BAD_REQUEST);
        }else{
            Optional<TimeTaskEntity> oldTimeTaskEntity = timeTaskRepository.findById(timeTaskEntity.getId());
            if(oldTimeTaskEntity.isEmpty()){
                return new ResponseEntity<>("該排程不存在", HttpStatus.BAD_REQUEST);
            }else{
                timeTaskRepository.save(timeTaskEntity);
                return new ResponseEntity<>("更新排程成功", HttpStatus.OK);
            }
        }
    }

    @Operation(summary = "刪除排程")
    @DeleteMapping("/uiDeleteTimeTask/{id}")
    public ResponseEntity<String> uiDeleteTimeTask(@PathVariable("id") int id) throws Exception{
        Optional<TimeTaskEntity> oldTimeTaskEntity = timeTaskRepository.findById(id);
        if(oldTimeTaskEntity.isEmpty()){
            return new ResponseEntity<>("該排程不存在", HttpStatus.BAD_REQUEST);
        }else{
            timeTaskRepository.deleteById(id);
            return new ResponseEntity<>("刪除排程成功", HttpStatus.OK);
        }
    }
}
