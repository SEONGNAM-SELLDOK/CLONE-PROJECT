package com.selldok.toy.employee.model;

import com.selldok.toy.employee.entity.AppliedCompany.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author DongSeok,Kim
 */
@Getter
@Setter
@ToString
public class AppliedCompanyDto {
    private Long applicantId;
    private String name;
    private String email;
    private String phoneNumber;
    private Long companyId;
    private Status status;
}
