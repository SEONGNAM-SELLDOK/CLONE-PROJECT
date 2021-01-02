package com.selldok.toy.company.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Go Gisung
 */

@Getter
@Setter
public class NewHireListResponse {

    private Long id;
    private String companyName;
    private String image;

    @QueryProjection
    public NewHireListResponse(Long id, String image, String companyName) {
        this.id = id;
        this.image = image;
        this.companyName = companyName;
    }
}
