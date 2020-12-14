package com.selldok.toy.company.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @gogisung
 * */
@Data
public class BoardSearchCondition {
    private String title;
    private String companyName;
    private String businessNum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}

