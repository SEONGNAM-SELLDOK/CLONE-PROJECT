package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
<<<<<<< HEAD
import com.selldok.toy.company.entity.City;
import com.selldok.toy.company.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

=======
import lombok.Data;
import lombok.NoArgsConstructor;

>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
/**
 * @gogisung
 * */
@Data
@NoArgsConstructor
public class BoardReadResponse {
    private String title;
    private String content;
    private String image;
<<<<<<< HEAD
    private LocalDate endDate;
=======
    private String endDate;
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
    private String companyName;
    private String companyCountry;
    private String companyCity;
    private String companyStreet;

    @QueryProjection
<<<<<<< HEAD
    public BoardReadResponse(String title, String content, String image, LocalDate endDate, String companyName, String companyCountry, String companyCity, String companyStreet) {
=======
    public BoardReadResponse(String title, String content, String image, String endDate, String companyName, String companyCountry, String companyCity, String companyStreet) {
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
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
<<<<<<< HEAD

=======
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
