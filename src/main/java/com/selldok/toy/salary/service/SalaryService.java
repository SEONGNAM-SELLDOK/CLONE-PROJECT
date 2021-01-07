package com.selldok.toy.salary.service;

import com.selldok.toy.salary.dao.SalaryRepository;
import com.selldok.toy.salary.entity.Occupation;
import com.selldok.toy.salary.entity.Salary;
import com.selldok.toy.salary.entity.SalaryInfo;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

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
        Salary salary1 = new Salary(Occupation.dotNet,new SalaryInfo(1000,2000,3000,4000,5000,6000,7000,8000,9000,10000));
        Salary salary2 = new Salary(Occupation.java,new SalaryInfo(800,1000,1200,1400,2000,3000,5000,6000,7000,8000));
        Salary salary3 = new Salary(Occupation.embedded,new SalaryInfo(1500,2000,2500,3000,4500,5500,7500,8500,9000,11000));
        Salary salary4 = new Salary(Occupation.fe,new SalaryInfo(500,1000,1500,2500,3000,3500,5500,6000,7000,7500));
        salaryRepository.save(salary1); salaryRepository.save(salary2); salaryRepository.save(salary3); salaryRepository.save(salary4);
    }

    @Transactional(readOnly = true)
    public SalaryResponse getSalaryById(Long id){
        if(id > salaryRepository.count()){
            id = 0L;
        }

        Salary salary = salaryRepository.findById(id).get();
        SalaryInfo salaryInfo = salary.getSalaryInfo();

        SalaryResponse salaryResponse = new SalaryResponse(salary.getOccupation(),salaryInfo.getYearOne()
                                            ,salaryInfo.getYearTwo(),salaryInfo.getYearThree()
                                            ,salaryInfo.getYearFour(),salaryInfo.getYearFive()
                                            ,salaryInfo.getYearSix(),salaryInfo.getYearSeven()
                                            ,salaryInfo.getYearEight(),salaryInfo.getYearNine()
                                            ,salaryInfo.getYearTen());
        return salaryResponse;
    }

    public void updateSalary(Long id,SalaryRequest salaryRequest){
        Occupation occupation;
        if(id == 1) occupation = Occupation.dotNet;
        else if(id==2) occupation = Occupation.java;
        else if(id==3) occupation = Occupation.embedded;
        else occupation = Occupation.fe;

        Salary salary = salaryRepository.findByOccupation(occupation);
        salary.changeSalary(salaryRequest);
        System.out.println(salary);
    }
}
