package com.selldok.toy.repository;

import com.selldok.toy.company.model.CompanyListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * @gogisung
 * */
public interface CompanyRepositoryCustom {
    List<CompanyListResponse> search(CompanySearchCondition condition);
    Page<CompanyListResponse> searchPage(CompanySearchCondition condition, Pageable pageable);
}
