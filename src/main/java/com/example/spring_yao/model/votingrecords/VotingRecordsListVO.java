package com.example.spring_yao.model.votingrecords;

import lombok.Data;

@Data
public class VotingRecordsListVO {

    /** 主鍵 */
    private int id;

    /** 投票項目編號 */
    private String itemCode;

    /** 投票者 */
    private String voterName;

    /** 虛擬欄位 投票項目名稱 */
    private String itemName;

    private String rowStatus;
}
