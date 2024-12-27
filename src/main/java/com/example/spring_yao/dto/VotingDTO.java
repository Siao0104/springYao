package com.example.spring_yao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotingDTO {
    private int id;
    private String itemCode;
    private String voterName;
    private String itemName;
}

