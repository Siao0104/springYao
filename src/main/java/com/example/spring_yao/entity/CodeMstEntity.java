package com.example.spring_yao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="code_mst")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class CodeMstEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 版本 */
    @Column(name = "version")
    @org.springframework.data.annotation.Version
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

    @JsonManagedReference
    @OneToMany(mappedBy = "codeMstEntity",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<CodeDtlEntity> codeDtlEntities;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modify_by")
    private String lastModifyBy;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_modify_date")
    private Date lastModifyDate;

    @org.springframework.data.annotation.Transient
    private transient String rowStatus = "R";
}