package com.example.spring_yao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@Table(name="user_calendar")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class UserCalendarEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "execute_day")
    private Date executeDay;

    /** 使用者名稱 */
    @Column(name = "user_name")
    private String userName;

    /** 代辦事項 */
    @Column(name = "remark")
    private String remark;

    /** 重要否 */
    @Column(name = "enabled")
    private boolean enabled;

    /** 版本 */
    @Column(name = "version")
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modify_by")
    private String lastModifyBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "last_modify_date")
    private Date lastModifyDate;
}
