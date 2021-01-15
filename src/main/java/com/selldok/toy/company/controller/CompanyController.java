package com.selldok.toy.company.controller;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.dao.CompanySearchCondition;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyCreateRequest;
import com.selldok.toy.company.model.CompanyListResponse;
import com.selldok.toy.company.model.CompanyProfileResponse;
import com.selldok.toy.company.model.CompanyUpdateRequest;
import com.selldok.toy.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author Gogisung
 */

@Controller
@Slf4j
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @GetMapping
    public String getCompanyCreate() {
        return "company/create";
    }

    @GetMapping("read")
    public String getCompanyRead() {
        return "company/read";
    }

    @GetMapping("list")
    public String getCompanyList() {return "company/list"; }

    @PostMapping //기업 서비스 가입
    public ResponseEntity<String> create(final @Valid @RequestBody CompanyCreateRequest request) {
        log.info(request.toString());

        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Company company = Company.builder()
                .name(request.getName())
                .address(address)
                .businessNum(request.getBusinessNum())
                .totalSales(request.getTotalSales())
                .employees(request.getEmployees())
                .employees(request.getEmployees())
                .info(request.getInfo())
                .email(request.getEmail())
                .since(request.getSince())
                .phone(request.getPhone())
                .homepage(request.getHomepage())
                .terms(request.getTerms())
                .build();

        Long companyId = companyService.create(company);

        HashMap<String, Long> map = new HashMap<>();
        map.put("company_id", companyId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/companies") // List and paging
    @ResponseBody
    public ResponseEntity list(CompanySearchCondition condition, Pageable pageable) {
        Page<CompanyListResponse> companyListRequests = companyRepository.searchPage(condition, pageable);
        return new ResponseEntity(companyListRequests, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<CompanyProfileResponse> getProfile(@PathVariable("id") Long id) {
        return new ResponseEntity(companyRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, final @Valid @RequestBody CompanyUpdateRequest request) {

        Long companyId = companyService.update(id, request);

        HashMap<String, Long> map = new HashMap<>();
        map.put("company_id", companyId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        companyService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
