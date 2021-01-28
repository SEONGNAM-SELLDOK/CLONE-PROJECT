package com.selldok.toy.repository;

import com.selldok.toy.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Gogisung
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    List<Company> findByBusinessNum(String businessNum);

    /**
     * 사용하지 않는 것 같아 수정했습니다.
     */
    Optional<Company> findByName(String name);
}
