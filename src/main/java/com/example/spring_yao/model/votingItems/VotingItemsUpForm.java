package com.example.spring_yao.model.votingItems;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class VotingItemsUpForm {

    /** 主鍵 */
    private int id;

    /** 投票項目編號 */
    @NotBlank(message = "投票項目編號不能為空")
    @Length(message = "投票項目編號不能超過{max}個字",max = 50)
    private String itemCode;

    /** 投票項目名稱 */
    @NotBlank(message = "投票項目編號不能為空")
    @Length(message = "投票項目編號不能超過{max}個字",max = 100)
    private String itemName;

    private String rowStatus="U";
}
