package com.example.spring_yao.entity;

import com.example.spring_yao.utils.jwt.userauthority.UserAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_basic")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Component
@Data
public class UserBasicEntity implements Serializable {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 版本 */
    @Column(name = "version")
    @Version
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "birthday")
    private Date birthday;

    /** 國籍 */
    @Column(name = "country")
    private String country;

    /** 性別 */
    @Column(name = "sex")
    private String sex;

    /** email */
    @Column(name = "email")
    private String email;

    /** 使用者生效否 */
    @Column(name = "enabled")
    private boolean enabled;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "expiry_date")
    private Date expiryDate;

    /** token */
    @Column(name = "token")
    private String token;

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

    /** 使用者權限 */
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private UserAuthority authority;
}
