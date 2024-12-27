package com.example.spring_yao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotingTotalDTO {
    private Long totalVotes;
    private String itemCode;
    private String itemName;
}
