package com.example.spring_yao.model.userbasic;

import com.example.spring_yao.utils.jwt.userauthority.UserAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class UserBasicCrForm {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 姓名 */
    @NotBlank(message = "姓名不能為空")
    @Length(message = "名稱不能超過{max}個字",max = 10)
    private String userName;

    /** 帳號 */
    @NotBlank(message = "帳號不能為空")
    @Length(message = "帳號不能超過{max}個字",max = 20)
    private String account;

    /** 密碼 */
    @NotBlank(message = "密碼不能為空")
    @Length(message = "帳號不能超過{max}個字",max = 16)
    private String password;

    /** 生日 */
    @NotNull(message = "生日不能為空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /** 國籍 */
    @NotBlank(message = "國籍不能為空")
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

    /** token */
    private String token;
}
