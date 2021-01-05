package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @gogisung
 * */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
