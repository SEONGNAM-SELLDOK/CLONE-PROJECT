package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author Gogisung
 */

@Service
@Transactional
@RequiredArgsConstructor // final의 기본 생성자를 만들어준다
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * 파일 업로드
     * */
    private String saveUploadFile(MultipartFile upload_file) {
        String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
        try{
            upload_file.transferTo(new File("C:/Users/ggs/Documents/workspaces/CLONE-PROJECT/src/main/resources/upload/" + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
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
