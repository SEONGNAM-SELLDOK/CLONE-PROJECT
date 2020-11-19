package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.service.EmployeeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Incheol Jung
 */
@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {this.employeeService = employeeService;}

    @GetMapping
    public List<Employee> get(){
        return employeeService.get();
    }
}
