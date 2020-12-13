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
    List<BoardReadResponse> read(Long id);
    Page<BoardListResponse> searchBoardPage(Pageable pageable);
}
