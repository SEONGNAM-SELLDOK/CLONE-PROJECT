package com.selldok.toy.employee.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
public class InsertEmployeeRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
