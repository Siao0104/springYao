package com.example.spring_yao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@Table(name="base_timetask")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class TimeTaskEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 執行方法名稱 */
    @Column(name = "task_name")
    private String taskName;

    /** 執行方法敘述 */
    @Column(name = "task_desc")
    private String taskDesc;

    /** 排程執行時間 */
    @Column(name = "cron_expression")
    private String cronExpression;

    /** 排程說明 */
    @Column(name = "remark")
    private String remark;

    /** 上次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    /** 下次執行時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "next_execute_time")
    private Date nextExecuteTime;
}
