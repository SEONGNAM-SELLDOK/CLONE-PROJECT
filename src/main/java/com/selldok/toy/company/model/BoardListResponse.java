package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
/**
 * @gogisung
 * */
=======
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263

@Data
@NoArgsConstructor
public class BoardListResponse {
<<<<<<< HEAD
    private Long id;
=======
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
    private String title;
    private String image;
    private String companyName;
    private String companyCountry;
    private String companyCity;

    @QueryProjection
<<<<<<< HEAD
    public BoardListResponse(Long id, String title, String image, String companyName, String companyCountry, String companyCity) {
        this.id = id;
=======
    public BoardListResponse(String title, String image, String companyName, String companyCountry, String companyCity) {
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
        this.title = title;
        this.image = image;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
    }
}
