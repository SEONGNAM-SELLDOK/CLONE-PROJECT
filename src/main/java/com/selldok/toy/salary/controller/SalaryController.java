package com.selldok.toy.salary.controller;

import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import com.selldok.toy.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author Seil Park
 */
@Controller
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/salary")
    public String getSalaryView(){
        return "/salary/main";
    }

    @GetMapping("/salary/{id}")
    public ResponseEntity<SalaryResponse> getSalary(@PathVariable Long id){
        SalaryResponse salaryResponse = salaryService.getSalaryById(id);
        return new ResponseEntity<>(salaryResponse,HttpStatus.OK);
    }

    @PutMapping("/salary/{id}")
    public String updateSalary(@PathVariable Long id, SalaryRequest salaryRequest){
        salaryService.updateSalary(salaryRequest);
        return "redirect:/salary/" + id;
    }
}
