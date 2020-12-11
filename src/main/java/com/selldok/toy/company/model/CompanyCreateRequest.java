package com.selldok.toy.company.model;

import com.selldok.toy.company.entity.City;
import com.selldok.toy.company.entity.Country;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Gogisung
 */

@Getter
@Setter
public class CompanyCreateRequest {
    private String name;
    private String country;
    private String city;
    private String street;
    private String businessNum; // 사업자 번호
    private String totalSales; // 매출액
    private String employees; // 직원수
    private String info; // 회사소개
    private String email; // 대표 이메일
    private String since; // 설립연도 ex)2012년
    private String phone; // 대표전화
    private String homepage; // 대표사이트 필수 x
}
