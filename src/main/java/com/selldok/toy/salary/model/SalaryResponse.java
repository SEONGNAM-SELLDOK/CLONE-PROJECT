package com.selldok.toy.salary.model;

import com.selldok.toy.salary.entity.Occupation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {
    private Occupation occupation;

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
