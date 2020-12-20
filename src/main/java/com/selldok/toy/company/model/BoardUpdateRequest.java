package com.selldok.toy.company.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Gogisung
 */

@Getter
@Setter
public class BoardUpdateRequest {
    private String title; // 제목
    private String content; //내용
    private String image; // 썸네일 이미지
    private Date endDate; // 마감일
}
