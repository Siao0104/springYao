package com.example.spring_yao.model.votingrecords;

import lombok.Data;

@Data
public class VotingRecordsPagination {

    String voterName;
    private int page;
    private int size;
}
