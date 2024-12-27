package com.example.spring_yao.repository;

import com.example.spring_yao.dto.VotingDTO;
import com.example.spring_yao.dto.VotingTotalDTO;
import com.example.spring_yao.entity.VotingRecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotingRecordsRepository extends JpaRepository<VotingRecordsEntity,Integer>, JpaSpecificationExecutor<VotingRecordsEntity> {
    List<VotingRecordsEntity> findByItemCode(String itemCode);
    @Query("SELECT new com.example.spring_yao.dto.VotingDTO(vr.id, vr.itemCode, vr.voterName, vi.itemName) " +
            "FROM VotingRecordsEntity vr JOIN VotingItemsEntity vi ON vr.itemCode = vi.itemCode " +
            "WHERE vr.voterName = :voterName")
    List<VotingDTO> findByVoterNameWithItemName(@Param("voterName") String voterName);

    @Query("SELECT new com.example.spring_yao.dto.VotingTotalDTO(COUNT(vr.itemCode),vi.itemCode,vi.itemName) " +
            "FROM VotingItemsEntity vi LEFT JOIN VotingRecordsEntity vr " +
            "ON vi.itemCode=vr.itemCode " +
            "GROUP BY vi.itemCode,vi.itemName")
    List<VotingTotalDTO> countByItemCodeTotalVotes();
}
