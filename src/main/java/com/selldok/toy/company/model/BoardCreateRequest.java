package com.selldok.toy.company.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Gogisung
 */

@NoArgsConstructor
@Getter
@ToString
@Builder
@AllArgsConstructor
public class BoardCreateRequest {

    @NotBlank
    private String title; // 제목

    @NotBlank
    private String content; //내용

    @NotBlank
    private String image; // 썸네일 이미지

    @NotNull
    private Date endDate; // 마감일

    @NotNull
    private Long companyId;

}
