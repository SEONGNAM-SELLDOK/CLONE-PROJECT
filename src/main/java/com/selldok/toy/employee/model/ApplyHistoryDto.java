package com.selldok.toy.employee.model;

import com.selldok.toy.employee.entity.ApplyHistory.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ApplyHistory 용 create, update 입력 DTO
 * @author DongSeok,Kim
 */
@Getter
@Setter
@ToString
public class ApplyHistoryDto {
    private Long id;
    private Long applicantId;
    private String name;
    private String email;
    private String phoneNumber;
    private Long companyId;
    private Status status;
}
