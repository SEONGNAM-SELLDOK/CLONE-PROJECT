package com.selldok.toy.salary.model;

import com.selldok.toy.salary.entity.Occupation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author Seil Park
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRequest {
    private Occupation occupation;

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
