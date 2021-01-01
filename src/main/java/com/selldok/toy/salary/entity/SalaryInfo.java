package com.selldok.toy.salary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryInfo {
    private int firstSalary;

    private int secondSalary;

    private int thirdSalary;

    private int fourthSalary;

    private int fifthSalary;

    private int sixthSalary;

    private int seventhSalary;

    private int eighthSalary;

    private int ninthSalary;

    private int tenthSalary;

}
