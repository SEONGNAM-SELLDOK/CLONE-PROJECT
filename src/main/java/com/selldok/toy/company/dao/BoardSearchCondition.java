package com.selldok.toy.company.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

