package com.example.spring_yao.model.votingItems;

import lombok.Data;

@Data
public class VotingItemsListVO {

    /** 主鍵 */
    private int id;

    /** 投票項目編號 */
    private String itemCode;

    /** 投票項目名稱 */
    private String itemName;

    private String rowStatus="R";
}
