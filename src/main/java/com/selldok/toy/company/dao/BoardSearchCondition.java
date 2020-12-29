package com.selldok.toy.company.dao;

import com.selldok.toy.company.entity.Address;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @gogisung
 * */
@Data
public class BoardSearchCondition {
    private String title;
    private String companyName;
    private String businessNum;
    private String country;
    private String city;

    @Temporal(TemporalType.DATE)
    private Date endDate;
}

