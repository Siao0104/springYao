package com.example.spring_yao.model.userbasic;

import com.example.spring_yao.utils.jwt.userauthority.UserAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBasicListVO implements Serializable {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 姓名 */
    private String userName;

    /** 帳號 */
    private String account;

    /** 密碼 */
    private String password;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /** 國籍 */
    private String country;

    /** 性別 */
    private String sex;

    /** 使用者生效否 */
    private boolean enabled;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    /** 使用者權限 */
    private UserAuthority authority;
}
