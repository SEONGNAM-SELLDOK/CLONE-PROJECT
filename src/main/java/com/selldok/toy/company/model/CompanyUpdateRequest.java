package com.selldok.toy.company.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @gogisung
 * */
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class CompanyUpdateRequest {

    @NotNull
    private String name;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    private String totalSales; // 매출액
    private String employees; // 직원수
    private String info; // 회사소개

    @Email
    private String email; // 대표 이메일

    @NotNull
    private String phone; // 대표전화
    private String homepage; // 대표사이트 필수 x
}
