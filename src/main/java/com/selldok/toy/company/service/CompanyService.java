package com.selldok.toy.company.service;

import com.selldok.toy.repository.CompanyRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyUpdateRequest;
import com.selldok.toy.company.validation.Validation;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Gogisung
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final Validation validation;

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    /**
     * 모든 기업 정보 GET
     * */
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }

    /**
     * 기업 정보 등록
     * */
    public Long create(Company company) {
        companyRepository.save(company);
        return company.getId();
    }

    /**
     * 기업 정보 수정
     * */
    public Long update(Long id, CompanyUpdateRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());

        Optional<Company> company = companyRepository.findById(id);

        company.ifPresent(existingCompany -> {
            existingCompany.setName(request.getName());
            existingCompany.setAddress(address);
            existingCompany.setTotalSales(request.getTotalSales());
            existingCompany.setEmployees(request.getEmployees());
            existingCompany.setInfo(request.getInfo());
            existingCompany.setEmail(request.getEmail());
            existingCompany.setPhone(request.getPhone());
            existingCompany.setHomepage(request.getHomepage());
            companyRepository.save(existingCompany);
        });

        return id;
    }

    /**
     * 기업 정보 삭제
     * */
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }


    /**
     * wdlist에 쓰일 부분입니다.
     * 회사관련해서 정보를 어떻게 넣을지 애매해서
     * 필요한값만 임의로 넣었습니다.
     */
    public Company createTempCompany(String name){
        Optional<Company> optionalCompany = companyRepository.findByName(name);
        if(optionalCompany.isPresent()) return optionalCompany.get();
        Company company = Company.builder()
                .name(name)
                .businessNum("1234u701234")
                .since("2020")
                .phone("010-1234-5678")
                .terms(true)
                .build();
        companyRepository.save(company);
        return company;
    }
}
