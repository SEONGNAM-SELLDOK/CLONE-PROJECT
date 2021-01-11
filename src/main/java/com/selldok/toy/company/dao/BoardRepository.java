package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @gogisung
 * */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.recommendation = b.recommendation + 1 where b.id = :id")
    int boardCountPlus(@Param("id") Long id);
}
