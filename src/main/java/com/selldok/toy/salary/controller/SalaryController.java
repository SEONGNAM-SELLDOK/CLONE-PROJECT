package com.selldok.toy.salary.controller;

import com.selldok.toy.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/salary")
    public String getSalaryView(){
        return "/salary/main";
    }

}
