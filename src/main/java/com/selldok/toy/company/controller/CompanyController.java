package com.selldok.toy.company.controller;

import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.FormCompany;
import com.selldok.toy.company.model.UpdateCompany;
import com.selldok.toy.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author Gogisung
 */

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/company/create")
    public String getCompanyView(){
        return "company/create.html";
    }

    @PostMapping("/company") //기업 서비스 가입
    @ResponseBody
    public ResponseEntity create(@RequestBody FormCompany from, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Address address = new Address(from.getCountry(), from.getCity(), from.getStreet());

        Company company = new Company();

        company.setName(from.getName());
        company.setAddress(address);
        company.setBusinessNum(from.getBusinessNum());
        company.setTotalSales(from.getTotalSales());
        company.setEmployees(from.getEmployees());
        company.setInfo(from.getInfo());
        company.setEmail(from.getEmail());
        company.setSince(from.getSince());
        company.setPhone(from.getPhone());
        company.setHomepage(from.getHomepage());
        company.setTerms(from.isTerms());

        Long companyId = companyService.join(company);

        HashMap<String, Long> map = new HashMap<>();
        map.put("company_id", companyId);

        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/company")
    public ResponseEntity list() {
        return new ResponseEntity(companyService.findAllCompany(), HttpStatus.OK);
    }

    @PutMapping("/company/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UpdateCompany request) {
        companyService.update(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/company/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        companyService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }




}
