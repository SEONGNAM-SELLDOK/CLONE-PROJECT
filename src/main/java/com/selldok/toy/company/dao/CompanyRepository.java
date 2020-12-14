package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Gogisung
 */

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    List<Company> findByBusinessNum(String businessNum);
}
