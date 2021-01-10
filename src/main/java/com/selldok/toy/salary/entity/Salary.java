package com.selldok.toy.salary.entity;

import com.selldok.toy.salary.model.SalaryRequest;
import lombok.Builder;
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
        SalaryInfo newSalaryInfo = SalaryInfo.builder().yearOne(salaryRequest.getYearOne()).yearTwo(salaryRequest.getYearTwo())
                                                        .yearThree(salaryRequest.getYearThree()).yearFour(salaryRequest.getYearFour())
                                                        .yearFive(salaryRequest.getYearFive()).yearSix(salaryRequest.getYearSix())
                                                        .yearSeven(salaryRequest.getYearSeven()).yearEight(salaryRequest.getYearEight())
                                                        .yearNine(salaryRequest.getYearNine()).yearTen(salaryRequest.getYearTen()).build();
        this.salaryInfo = newSalaryInfo;
    }
}
