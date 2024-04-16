package com.example.spring_yao.entity;

import com.example.spring_yao.jwt.userauthority.UserAuthority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="user_basic")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Component
public class UserBasicEntity implements Serializable {

    /** 主鍵 */
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 版本 */
    @Column(name = "version")
    @org.springframework.data.annotation.Version
    private Integer version;

    /** 姓名 */
    @Column(name = "user_name")
    private String userName;

    /** 帳號 */
    @Column(name = "account")
    private String account;

    /** 密碼 */
    @Column(name = "password")
    private String password;

    /** 生日 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    /** 國籍 */
    @Column(name = "country")
    private String country;

    /** 性別 */
    @Column(name = "sex")
    private String sex;

    /** 使用者生效否 */
    @Column(name = "enabled")
    private boolean enabled;

    /** 到期日 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiry_date")
    private Date expiryDate;

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

    /** 使用者權限 */
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private UserAuthority authority;
}
