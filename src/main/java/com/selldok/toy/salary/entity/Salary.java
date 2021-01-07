package com.selldok.toy.salary.entity;

import com.selldok.toy.salary.model.SalaryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Embedded;

/**
 * @author Seil Park
 */
@Entity
@Getter
@NoArgsConstructor
@ToString
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Occupation occupation;

    @Embedded
    private SalaryInfo salaryInfo;

    public Salary(Occupation occupation, SalaryInfo salaryInfo) {
        this.occupation = occupation;
        this.salaryInfo = salaryInfo;
    }

    public void changeSalary(SalaryRequest salaryRequest) {
        SalaryInfo newSalaryInfo = new SalaryInfo(salaryRequest.getYearOne(), salaryRequest.getYearTwo(), salaryRequest.getYearThree(), salaryRequest.getYearFour(),
                salaryRequest.getYearFive(), salaryRequest.getYearSix(), salaryRequest.getYearSeven(), salaryRequest.getYearEight(), salaryRequest.getYearNine(), salaryRequest.getYearTen());
        this.salaryInfo = newSalaryInfo;
    }
}
