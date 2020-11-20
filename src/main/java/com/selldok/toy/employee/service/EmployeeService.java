package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Incheol Jung
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {this.employeeRepository = employeeRepository;}

    public List<Employee> get() {
        return employeeRepository.findAll();
    }

    public void insert(InsertEmployeeRequest request) {
        Employee employee = new Employee(request.getName());
        employeeRepository.save(employee);
    }

    public void update(Long id, UpdateEmployeeRequest request) {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.ifPresent(existingEmployee -> {
            existingEmployee.setName(request.getName());
            employeeRepository.save(existingEmployee);
        });
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
