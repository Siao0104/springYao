package com.example.spring_yao.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RestControllerAdvice
public class GlobalRestException {

    /**
     * 參數字段驗證錯誤 ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        List<String> result = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        String json = JsonUtils.objectToJson(result);
        log.warn(STR."\{e.getMessage()}，ConstraintViolationException : 字段驗證異常");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    /**
     * 參數字段驗證錯誤 BindException
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException e) {
        Map<String, String> messageMap = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        String json = JsonUtils.objectToJson(messageMap);
        log.warn(STR."\{e.getMessage()}，BindException : 字段驗證異常");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    /**
     * 參數字段驗證錯誤 MethodArgumentNotValidException
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for(int i = 0; i < allErrors.size(); ++i) {
            if(i==0){
                errorMsg.append(((ObjectError) allErrors.get(i)).getDefaultMessage());
            }else{
                errorMsg.append("\n").append(((ObjectError) allErrors.get(i)).getDefaultMessage());
            }
        }
        log.warn(STR."\{e.getMessage()}，MethodArgumentNotValidException : 字段驗證異常");
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
}
