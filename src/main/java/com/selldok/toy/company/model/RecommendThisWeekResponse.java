package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendThisWeekResponse {
    private long id;
    private String title;
    private String companyName;
    private String companyCountry;
    private String companyCity;
    private String image;

    @QueryProjection
    public RecommendThisWeekResponse(long id, String title, String companyName, String companyCountry, String companyCity, String image) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
        this.image = image;
    }
}
