package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.dao.PersonInfoRepository;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Company;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.entity.Expertise;
import com.selldok.toy.employee.entity.PersonInfo;
import com.selldok.toy.employee.entity.School;
import com.selldok.toy.employee.mapper.EmployeeMapper;
import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;
import com.selldok.toy.employee.model.UpdateProfileRequest;

import org.springframework.stereotype.Service;

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
    private final PersonInfoRepository personInfoRepository;

    public Optional<Employee> get(Long id) {
        return employeeRepository.findById(id);
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

    public EmployeeProfileResponse getProfile(Long id) {
        return employeeMapper.getEmployee(id);
    }

    public void updateProfile(Long id, UpdateProfileRequest request) {
        Optional<Employee> employee = employeeRepository.findById(id);

        employee.ifPresent(existingEmployee -> {
            Optional<PersonInfo> optionalPersonInfo = personInfoRepository.findByEmployeeId(existingEmployee.getId());
            PersonInfo personInfo = new PersonInfo();
            optionalPersonInfo.ifPresent(existingPersonInfo -> personInfo.setId(existingEmployee.getId()));

            personInfo.setResume(request.getResume());
            personInfo.setCompany(Company.builder()
                                         .companyName(request.getCompany().getCompanyName())
                                         .position(request.getCompany().getPosition())
                                         .rank(request.getCompany().getRank())
                                         .build());
            personInfo.setExpertise(Expertise.builder()
                                             .carrer(request.getExpertise().getCarrer())
                                             .occupation(request.getExpertise().getOccupation())
                                             .skills(request.getExpertise().getSkills())
                                             .task(request.getExpertise().getTask())
                                             .build());
            personInfo.setSchool(School.builder()
                                       .schoolName(request.getSchool().getSchoolName())
                                       .department(request.getSchool().getDepartment())
                                       .build());

            personInfoRepository.save(personInfo);
        });
    }

    public Employee insert(String name, String email, String url) {
        Employee employee = new Employee(name, email, "");
        return employeeRepository.save(employee);
    }
}
