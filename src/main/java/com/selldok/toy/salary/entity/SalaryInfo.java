package com.selldok.toy.salary.entity;

import lombok.*;

import javax.persistence.Embeddable;
/**
 * @author Seil Park
 */
@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryInfo {
    private int yearOne;

    private int yearTwo;

    private int yearThree;

    private int yearFour;

    private int yearFive;

    private int yearSix;

    private int yearSeven;

    private int yearEight;

    private int yearNine;

    private int yearTen;

}
