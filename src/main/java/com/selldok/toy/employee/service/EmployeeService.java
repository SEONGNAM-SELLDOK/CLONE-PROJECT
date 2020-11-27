package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.mapper.EmployeeMapper;
import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

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

    public List<EmployeeProfileResponse> getProfile(Long id) {
        return employeeMapper.getEmployee();
    }
}
