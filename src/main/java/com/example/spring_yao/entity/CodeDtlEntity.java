package com.example.spring_yao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="code_dtl")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class CodeDtlEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 版本 */
    @Column(name = "version")
    @Version
    private Integer version;

    /** 代碼 */
    @Column(name = "code")
    private String code;

    /** 代碼描述 */
    @Column(name = "code_desc")
    private String codeDesc;

    /** 生效否 */
    @Column(name = "enabled")
    private String enabled;

    /** 外鍵 */
    @ManyToOne
    @JoinColumn(name = "code_mst_id")
    @JsonBackReference
    private CodeMstEntity codeMstEntity;

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
