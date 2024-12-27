package com.example.spring_yao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name="voting_records")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class VotingRecordsEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 投票項目編號 */
    @Column(name = "item_code")
    private String itemCode;

    /** 投票者 */
    @Column(name = "voter_name")
    private String voterName;

    @Transient
    private String rowStatus="R";
}
