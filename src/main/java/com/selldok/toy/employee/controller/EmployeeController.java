package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;
import com.selldok.toy.employee.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Employee> get() {
        return employeeService.get();
    }

    @PostMapping
    public Employee insert(@RequestBody InsertEmployeeRequest request) { return employeeService.insert(request); }

    @PutMapping("{id}")
    public void update(@PathVariable("id") Long id, @RequestBody UpdateEmployeeRequest request) { employeeService.update(id, request); }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) { employeeService.delete(id); }
}
