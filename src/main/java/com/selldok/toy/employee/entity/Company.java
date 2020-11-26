package com.selldok.toy.employee.entity;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Embeddable
@Getter
@Setter
public class Company {
    private String companyName;
    private String position;
    private String rank;
}
