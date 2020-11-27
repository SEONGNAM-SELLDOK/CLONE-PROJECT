package com.selldok.toy.employee.mapper;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.EmployeeProfileResponse;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Incheol Jung
 */
@Repository
@Mapper
public interface EmployeeMapper {
    List<EmployeeProfileResponse> getEmployee();
}
