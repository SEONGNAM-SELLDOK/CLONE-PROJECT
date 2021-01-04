package com.selldok.toy.salary.entity;

import com.selldok.toy.salary.model.SalaryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        SalaryInfo newSalaryInfo = new SalaryInfo(salaryRequest.getOneYear(), salaryRequest.getTwoYear(), salaryRequest.getThreeYear(), salaryRequest.getFourYear(),
                salaryRequest.getFiveYear(), salaryRequest.getSixYear(), salaryRequest.getSevenYear(), salaryRequest.getEightYear(), salaryRequest.getNineYear(), salaryRequest.getTenYear());
        this.salaryInfo = newSalaryInfo;
    }
}
