package com.example.spring_yao.model.codemst;

import lombok.Data;

@Data
public class CodeMstPagination {

    /**查詢條件類型*/
    String dataType;
    private int page;
    private int size;
}
