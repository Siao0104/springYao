package com.example.spring_yao.model.codemst;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CodeMstCrForm {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 代碼 */
    @NotBlank(message = "代碼不能為空")
    private String code;

    /** 代碼描述 */
    @NotBlank(message = "代碼描述不能為空")
    private String codeDesc;

    /** 生效否 */
    private String enabled;
}
