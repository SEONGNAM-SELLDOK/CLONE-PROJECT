package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;
import com.selldok.toy.employee.model.UpdateProfileRequest;
import com.selldok.toy.employee.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Incheol Jung
 */
@Controller
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {this.employeeService = employeeService;}

    @GetMapping("view")
    public String getEmployeeView() {
        return "employee/employee.html";
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Employee> get(@PathVariable("id") Long id) {
        return new ResponseEntity(employeeService.get(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity insert(@RequestBody InsertEmployeeRequest request) {
        employeeService.insert(request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UpdateEmployeeRequest request) {
        employeeService.update(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("profile/{id}")
    @ResponseBody
    public ResponseEntity<EmployeeProfileResponse> getProfile(@PathVariable("id") Long id) {
        return new ResponseEntity(employeeService.getProfile(id), HttpStatus.OK);
    }

    @PutMapping("profile/{id}")
    @ResponseBody
    public ResponseEntity<EmployeeProfileResponse> updateProfile(@PathVariable("id") Long id,
                                                                 @RequestBody UpdateProfileRequest request) {
        employeeService.updateProfile(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
