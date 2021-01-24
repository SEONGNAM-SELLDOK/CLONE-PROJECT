package com.selldok.toy.company.model;

import lombok.*;

import javax.validation.constraints.*;

/**
 * @author Gogisung
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CompanyCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String country;

    @NotNull
    private String city;
    @NotNull
    private String street;

    @NotNull
    private String businessNum; // 사업자 번호
    private String totalSales; // 매출액
    private String employees; // 직원수
    private String info; // 회사소개

    @Email
    private String email; // 대표 이메일

    @NotNull
    private String since; // 설립연도 ex)2012년

    @NotNull
    private String phone; // 대표전화
    private String homepage; // 대표사이트 필수 x

    @AssertTrue
    private Boolean terms;
}
