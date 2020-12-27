package com.selldok.toy.company.dao;

import com.selldok.toy.company.model.BoardListResponse;
import com.selldok.toy.company.model.BoardReadResponse;
import com.selldok.toy.company.model.NewHireListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @gogisung
 * */
public interface BoardRepositoryCustom {
    List<BoardReadResponse> findByBoardInfo(Long id);
    List<NewHireListResponse> newHireByBoardInfo();
    Page<BoardListResponse> searchBoard(BoardSearchCondition condition, Pageable pageable);
}
