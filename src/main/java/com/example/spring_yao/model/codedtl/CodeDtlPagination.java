package com.example.spring_yao.model.codedtl;

import lombok.Data;

@Data
public class CodeDtlPagination {

    /** 外鍵 */
    int codeMstId;

    private int page;
    private int size;
}
