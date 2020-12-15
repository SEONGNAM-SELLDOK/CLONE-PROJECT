package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.CompanyRepository;
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
    public void update(Long id, CompanyUpdateRequest request) {
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
    }

    /**
     * 기업 정보 삭제
     * */
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }
}
