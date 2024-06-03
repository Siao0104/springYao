package com.example.spring_yao.model.timetask;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class TimeTaskCrForm {

    /** 主鍵 */
    private int id;

    /** 執行方法名稱 */
    @NotBlank(message = "執行方法名稱不能為空")
    @Length(message = "執行方法名稱不能超過{max}個字",max = 50)
    private String taskName;

    /** 執行方法敘述 */
    @NotBlank(message = "執行方法敘述不能為空")
    @Length(message = "執行方法名稱不能超過{max}個字",max = 50)
    private String taskDesc;

    /** 排程執行時間 */
    @NotBlank(message = "排程執行時間不能為空")
    @Length(message = "排程執行時間不能超過{max}個字",max = 50)
    private String cronExpression;

    /** 排程說明 */
    private String remark;

    /** 上次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastExecuteTime;

    /** 下次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date nextExecuteTime;
}
