package com.selldok.toy.company.dao;

import com.selldok.toy.company.model.BoardListResponse;
import com.selldok.toy.company.model.BoardReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @gogisung
 * */
public interface BoardRepositoryCustom {
<<<<<<< HEAD
    List<BoardReadResponse> findByBoardInfo(Long id);
    Page<BoardListResponse> searchBoard(BoardSearchCondition condition, Pageable pageable);
=======
    List<BoardReadResponse> read(Long id);
    Page<BoardListResponse> searchBoardPage(Pageable pageable);
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
}
