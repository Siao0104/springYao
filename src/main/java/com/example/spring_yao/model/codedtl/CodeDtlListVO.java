package com.example.spring_yao.model.codedtl;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeDtlListVO implements Serializable{

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

    /** 外鍵 */
    private int codeMstId;
}
