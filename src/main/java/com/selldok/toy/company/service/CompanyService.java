package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Gogisung
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final의 기본 생성자를 만들어준다
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * 기업 정보 등록
     * */
    @Transactional
    public Long join(Company company) {
        validateDuplicateCompany(company); // 중복 기업 검증
        companyRepository.save(company);
        return company.getId();
    }

    private void validateDuplicateCompany(Company company) {
        List<Company> byBusinessNum = companyRepository.findByBusinessNum(company.getBusinessNum());
        if(!byBusinessNum.isEmpty()) {
            throw new IllegalStateException("이미 등록된 기업입니다.");
        }
    }

    //기업 전체 조회
    public List<Company> findCompany() {
        return companyRepository.findAll();
    }

}
