package com.example.spring_yao.controller;

import com.example.spring_yao.entity.UserCalendarEntity;
import com.example.spring_yao.model.usercalendar.UserCalendarCrForm;
import com.example.spring_yao.model.usercalendar.UserCalendarListVO;
import com.example.spring_yao.repository.UserCalendarRepository;
import com.example.spring_yao.service.UserCalendarService;
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

@RestController
@RequestMapping("springYao/userCalendar")
@Slf4j
@Tag(name = "會員行事曆")
public class UserCalendarController {

    @Autowired
    private UserCalendarService userCalendarService;

    @Autowired
    private UserCalendarRepository userCalendarRepository;

    @Operation(summary = "查詢個人全部行事曆")
    @GetMapping("/uiGetAllCalendar/{userName}")
    public ResponseEntity<List<UserCalendarListVO>> uiGetAllCalendar(@PathVariable("userName") String userName){
        List<UserCalendarEntity> userCalendarEntities = userCalendarRepository.findAllByUserName(userName);
        List<UserCalendarListVO> userCalendarListVOS = JsonUtils.listTolist(userCalendarEntities,UserCalendarListVO.class);
        return ResponseEntity.ok(userCalendarListVOS);
    }

    @Operation(summary = "新增個人行事曆")
    @PostMapping("/uiAddOwnCalendar")
    public ResponseEntity<String> uiAddOwnCalendar(@RequestBody @Valid UserCalendarCrForm userCalendarCrForm){
        UserCalendarEntity userCalendarEntity = JsonUtils.objectToObject(userCalendarCrForm,UserCalendarEntity.class);
        userCalendarRepository.save(userCalendarEntity);
        return new ResponseEntity<>("新增個人行事曆成功", HttpStatus.OK);
    }
}
