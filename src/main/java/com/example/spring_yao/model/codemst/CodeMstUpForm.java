package com.example.spring_yao.model.codemst;

import lombok.Data;

@Data
public class CodeMstUpForm {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 代碼 */
    private String code;

    /** 代碼描述 */
    private String codeDesc;

    /** 生效否 */
    private String enabled;
}
