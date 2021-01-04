package com.selldok.toy.salary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
/**
 * @author Seil Park
 */
@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryInfo {
    private int oneYear;

    private int twoYear;

    private int threeYear;

    private int fourYear;

    private int fiveYear;

    private int sixYear;

    private int sevenYear;

    private int eightYear;

    private int nineYear;

    private int tenYear;

}
