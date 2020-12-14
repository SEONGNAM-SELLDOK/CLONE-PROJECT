package com.selldok.toy.company.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gogisung
 */

@Getter
@Setter
public class BoardCreateRequest {
    private String title; // 제목
    private String content; //내용
    private String image; // 썸네일 이미지
    private LocalDate endDate; // 마감일
    private Long companyId;
}
