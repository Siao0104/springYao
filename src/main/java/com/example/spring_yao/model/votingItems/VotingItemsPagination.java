package com.example.spring_yao.model.votingItems;

import lombok.Data;

@Data
public class VotingItemsPagination {

    /**查詢條件類型*/
    String dataType;
    private int page;
    private int size;
}
