package com.selldok.toy.employee.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Incheol Jung
 */
@Embeddable
public class Company {
    private String companyName;
    private String position;
    private String rank;
}
