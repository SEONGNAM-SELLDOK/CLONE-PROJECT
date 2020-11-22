package com.selldok.toy.company.controller;

import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyForm;
import com.selldok.toy.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * @author Gogisung
 */

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("view")
    public String getCompanyView(){
        return "company/create.html";
    }

    @PostMapping("/company") //기업 서비스 가입
    @ResponseBody
    public ResponseEntity create(@RequestBody CompanyForm from, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Address address = new Address(from.getCountry(), from.getCity(), from.getStreet());

        Company company = new Company();

        company.setName(from.getName());
        company.setAddress(address);
        company.setBusinessNum(from.getBusinessNum());
        company.setTotalSales(from.getTotalSales());
//        company.setIndustries(from.getIndustries());
        company.setEmployees(from.getEmployees());
        company.setInfo(from.getInfo());
        company.setEmail(from.getEmail());
        company.setSince(from.getSince());
        company.setPhone(from.getPhone());
        company.setHomepage(from.getHomepage());
        company.setTerms(from.isTerms());

        companyService.join(company);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
