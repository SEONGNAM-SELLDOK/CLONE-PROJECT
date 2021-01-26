package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @gogisung
 * */
@Data
@Builder
@NoArgsConstructor
public class BoardListResponse {
    private Long id;
    private String title;
    private String image;
    private String companyName;
    private String companyCountry;
    private String companyCity;

    @QueryProjection
    public BoardListResponse(Long id, String title, String image, String companyName, String companyCountry, String companyCity) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
    }
}
