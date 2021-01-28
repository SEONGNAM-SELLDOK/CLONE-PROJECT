package com.selldok.toy.salary.service;

import com.selldok.toy.repository.SalaryRepository;
import com.selldok.toy.salary.entity.Occupation;
import com.selldok.toy.salary.entity.Salary;
import com.selldok.toy.salary.entity.SalaryInfo;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Seil Park
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    /**
     * 직군이 정해져있어, Create와 Delete는 어울리지 않을 것 같다 생각해 아래와 같이 했습니다.
     */
    @PostConstruct
    public void prev(){
        Salary salary1 = new Salary(Occupation.dotNet,SalaryInfo.builder()
                                                    .yearOne(1000).yearTwo(2000).yearThree(3000).yearFour(4000).yearFive(5000)
                                                    .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build());
        Salary salary2 = new Salary(Occupation.java,SalaryInfo.builder()
                .yearOne(1200).yearTwo(1500).yearThree(3000).yearFour(4000).yearFive(5000)
                .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build());
        Salary salary3 = new Salary(Occupation.embedded,SalaryInfo.builder()
                .yearOne(900).yearTwo(1500).yearThree(1800).yearFour(2300).yearFive(2800)
                .yearSix(5000).yearSeven(6000).yearEight(6500).yearNine(7500).yearTen(8000).build());
        Salary salary4 = new Salary(Occupation.fe,SalaryInfo.builder()
                .yearOne(1500).yearTwo(2000).yearThree(2500).yearFour(3000).yearFive(5000)
                .yearSix(6500).yearSeven(7000).yearEight(7500).yearNine(8000).yearTen(8800).build());
        salaryRepository.save(salary1); salaryRepository.save(salary2); salaryRepository.save(salary3); salaryRepository.save(salary4);
    }

    @Transactional(readOnly = true)
    public SalaryResponse getSalaryById(Long id){
        Salary salary = salaryRepository.findById(id).get();
        SalaryInfo salaryInfo = salary.getSalaryInfo();
        SalaryResponse salaryResponse = SalaryResponse.builder().
                                            occupation(salary.getOccupation())
                                            .yearOne(salaryInfo.getYearOne()).yearTwo(salaryInfo.getYearTwo())
                                            .yearThree(salaryInfo.getYearThree()).yearFour(salaryInfo.getYearFour())
                                            .yearFive(salaryInfo.getYearFive()).yearSix(salaryInfo.getYearSix())
                                            .yearSeven(salaryInfo.getYearSeven()).yearEight(salaryInfo.getYearEight())
                                            .yearNine(salaryInfo.getYearNine()).yearTen(salaryInfo.getYearTen())
                                            .build();
        return salaryResponse;
    }

    public void updateSalary(Long id,SalaryRequest salaryRequest){
        Optional<Salary> optionalSalary = salaryRepository.findById(id);
        Salary salary = optionalSalary.get();
        salary.changeSalary(salaryRequest);
    }
}
