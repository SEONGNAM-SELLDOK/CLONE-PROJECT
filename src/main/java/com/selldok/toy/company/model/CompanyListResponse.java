package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @gogisung
 * */
@Data
@NoArgsConstructor
public class CompanyListResponse {

    private String name; // 기업명
    private String employees; // 직원수
    private String email; // 대표 이메일
    private String phone; // 대표전화
    private String homepage; // 대표사이트
    private Long memberId;
    private String memberName;

    @QueryProjection
    public CompanyListResponse(String name, String employees, String email, String phone, String homepage, Long memberId, String memberName) {
        this.name = name;
        this.employees = employees;
        this.email = email;
        this.phone = phone;
        this.homepage = homepage;
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
