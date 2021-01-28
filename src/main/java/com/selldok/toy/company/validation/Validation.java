package com.selldok.toy.company.validation;

import com.selldok.toy.repository.CompanyRepository;
import com.selldok.toy.company.entity.Company;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Validation {

    private final CompanyRepository companyRepository;

    public List<Company> validateDuplicateCompany(Company company) {
        List<Company> byBusinessNum = companyRepository.findByBusinessNum(company.getBusinessNum());
        if(!byBusinessNum.isEmpty()) {
           throw new IllegalStateException("이미 등록된 기업입니다.");
        }
        return byBusinessNum;
    }
}
