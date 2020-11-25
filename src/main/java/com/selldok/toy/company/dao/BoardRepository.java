package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
