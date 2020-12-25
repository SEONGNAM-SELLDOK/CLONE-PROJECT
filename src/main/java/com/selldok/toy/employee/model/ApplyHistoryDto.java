package com.selldok.toy.employee.model;

import com.querydsl.core.annotations.QueryProjection;
import com.selldok.toy.employee.entity.ApplyHistory.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ApplyHistory용 create/update, select DTO
 * @author DongSeok,Kim
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApplyHistoryDto {
    private Long id;
    private Long applicantId;
    private String name;
    private String email;
    private String phoneNumber;
    private Long employmentBoardId;
    private String boardTitle;
    private String companyName;
    private String companyLogoUrl;
    private String appliedDate;
    private Status status;
    private String statusName;
    private String recommendStatus;

    @QueryProjection
    public ApplyHistoryDto(String name) {
        this.name = name;
    }
}
