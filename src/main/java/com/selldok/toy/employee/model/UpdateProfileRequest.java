package com.selldok.toy.employee.model;

import com.selldok.toy.employee.entity.Company;
import com.selldok.toy.employee.entity.Expertise;
import com.selldok.toy.employee.entity.School;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
public class UpdateProfileRequest {
    private String resume;
    private School school;
    private Company company;
    private Expertise expertise;
}
