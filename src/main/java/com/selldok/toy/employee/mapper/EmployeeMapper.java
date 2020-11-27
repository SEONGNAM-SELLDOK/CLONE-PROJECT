package com.selldok.toy.employee.mapper;

import com.selldok.toy.employee.model.EmployeeProfileResponse;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Incheol Jung
 */
@Repository
@Mapper
public interface EmployeeMapper {
    EmployeeProfileResponse getEmployee(Long id);
}
