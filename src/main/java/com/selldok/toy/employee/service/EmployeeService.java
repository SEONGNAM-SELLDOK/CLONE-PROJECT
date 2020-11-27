package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;

import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.AllArgsConstructor;

/**
 * @author Incheol Jung
 */
@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee get(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public void insert(InsertEmployeeRequest request) {
        Employee employee = new Employee(request.getName(), request.getEmail(), request.getPhoneNumber());
        employeeRepository.save(employee);
    }

    public void update(Long id, UpdateEmployeeRequest request) {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.ifPresent(existingEmployee -> {
            existingEmployee.setInfo(BasicInfo.builder()
                                              .name(request.getName())
                                              .email(request.getEmail())
                                              .phoneNumber(request.getPhoneNumber())
                                              .build());
            employeeRepository.save(existingEmployee);
        });
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
