package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Gogisung
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    List<Company> findByBusinessNum(String businessNum);
    Company findByName(String name);
}
