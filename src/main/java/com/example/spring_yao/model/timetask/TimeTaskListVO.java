package com.example.spring_yao.model.timetask;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TimeTaskListVO {

    /** 主鍵 */
    private int id;

    /** 執行方法名稱 */
    private String taskName;

    /** 執行方法敘述 */
    private String taskDesc;

    /** 排程執行時間 */
    private String cronExpression;

    /** 排程說明 */
    private String remark;

    /** 上次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastExecuteTime;

    /** 下次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextExecuteTime;
}
