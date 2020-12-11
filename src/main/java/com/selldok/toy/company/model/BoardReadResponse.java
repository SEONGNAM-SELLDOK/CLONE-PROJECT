package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import com.selldok.toy.company.entity.City;
import com.selldok.toy.company.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @gogisung
 * */
@Data
@NoArgsConstructor
public class BoardReadResponse {
    private String title;
    private String content;
    private String image;
    private LocalDate endDate;
    private String companyName;
    private String companyCountry;
    private String companyCity;
    private String companyStreet;

    @QueryProjection
    public BoardReadResponse(String title, String content, String image, LocalDate endDate, String companyName, String companyCountry, String companyCity, String companyStreet) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.endDate = endDate;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
        this.companyStreet = companyStreet;
    }
}

