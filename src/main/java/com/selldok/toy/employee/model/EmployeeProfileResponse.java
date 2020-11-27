package com.selldok.toy.employee.model;

import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.PersonInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
public class EmployeeProfileResponse {
    private long id;
    private BasicInfo info;
    private PersonInfo personInfo;
}
