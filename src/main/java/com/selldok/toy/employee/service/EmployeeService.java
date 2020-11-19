package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Incheol Jung
 */
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {this.employeeRepository = employeeRepository;}

    public List<Employee> get(){
        return employeeRepository.findAll();
    }
}
