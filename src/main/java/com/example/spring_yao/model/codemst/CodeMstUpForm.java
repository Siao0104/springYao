package com.example.spring_yao.model.codemst;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CodeMstUpForm {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 代碼 */
    @NotBlank(message = "代碼不能為空")
    @Length(message = "代碼不能超過{max}個字",max = 50)
    private String code;

    /** 代碼描述 */
    @NotBlank(message = "代碼描述不能為空")
    @Length(message = "代碼描述不能超過{max}個字",max = 50)
    private String codeDesc;

    /** 生效否 */
    private String enabled;

    private String rowStatus;
}
