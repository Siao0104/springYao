package com.example.spring_yao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name="voting_items")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class VotingItemsEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 投票項目編號 */
    @Column(name = "item_code")
    private String itemCode;

    /** 投票項目名稱 */
    @Column(name = "item_name")
    private String itemName;

    @Transient
    private String rowStatus="R";
}
