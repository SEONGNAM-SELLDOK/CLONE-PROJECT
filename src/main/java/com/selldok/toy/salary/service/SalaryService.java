package com.selldok.toy.salary.service;

import com.selldok.toy.salary.dao.SalaryRepository;
import com.selldok.toy.salary.entity.Occupation;
import com.selldok.toy.salary.entity.Salary;
import com.selldok.toy.salary.entity.SalaryInfo;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Seil Park
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    @PostConstruct
    public void prev(){
        Salary salary1 = new Salary(Occupation.dotNet,new SalaryInfo(1000,2000,3000,4000,5000,6000,7000,8000,9000,10000));
        Salary salary2 = new Salary(Occupation.java,new SalaryInfo(800,1000,1200,1400,2000,3000,5000,6000,7000,8000));
        Salary salary3 = new Salary(Occupation.embedded,new SalaryInfo(1500,2000,2500,3000,4500,5500,7500,8500,9000,11000));
        Salary salary4 = new Salary(Occupation.fe,new SalaryInfo(500,1000,1500,2500,3000,3500,5500,6000,7000,7500));
        salaryRepository.save(salary1); salaryRepository.save(salary2); salaryRepository.save(salary3); salaryRepository.save(salary4);
        System.out.println("=======================미리 집어넣기=====================");
    }

    public SalaryResponse getSalaryById(Long id){
        if(id > salaryRepository.count()){
            id = 0L;
        }

        Salary salary = salaryRepository.findById(id).get();
        SalaryInfo salaryInfo = salary.getSalaryInfo();

        SalaryResponse salaryResponse = new SalaryResponse(salary.getOccupation(),salaryInfo.getOneYear()
                                            ,salaryInfo.getTwoYear(),salaryInfo.getThreeYear()
                                            ,salaryInfo.getFourYear(),salaryInfo.getFiveYear()
                                            ,salaryInfo.getSixYear(),salaryInfo.getSevenYear()
                                            ,salaryInfo.getEightYear(),salaryInfo.getNineYear()
                                            ,salaryInfo.getTenYear());
        return salaryResponse;
    }

    public void setSalaryById(SalaryRequest salaryRequest){
        Optional<Salary> optionalSalary = salaryRepository.findByOccupation(salaryRequest.getOccupation());

        if(optionalSalary.get() == null){
            SalaryInfo salaryInfo = new SalaryInfo(salaryRequest.getOneYear(), salaryRequest.getTwoYear(), salaryRequest.getThreeYear()
                                            , salaryRequest.getFourYear(), salaryRequest.getFiveYear(), salaryRequest.getSixYear()
                                            , salaryRequest.getSevenYear(), salaryRequest.getEightYear(),salaryRequest.getNineYear(),salaryRequest.getTenYear());
            Salary newSalary = new Salary(salaryRequest.getOccupation(),salaryInfo);
            salaryRepository.save(newSalary);
        }
        else{
            Salary salary = optionalSalary.get();
            salary.changeSalary(salaryRequest);
        }
    }
}
