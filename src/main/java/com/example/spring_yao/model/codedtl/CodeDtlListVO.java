package com.example.spring_yao.model.codedtl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CodeDtlListVO implements Serializable{

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /** 代碼 */
    private String code;

    /** 代碼描述 */
    private String codeDesc;

    /** 生效否 */
    private String enabled;

    /** 外鍵 */
    private int codeMstId;
}
