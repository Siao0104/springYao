package com.example.spring_yao.model.codemst;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CodeMstListVO implements Serializable {

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

    private String rowStatus = "R";
}
