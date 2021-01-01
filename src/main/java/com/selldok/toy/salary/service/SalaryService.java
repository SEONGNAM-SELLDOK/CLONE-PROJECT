package com.selldok.toy.salary.service;

import com.selldok.toy.salary.dao.SalaryRepository;
import com.selldok.toy.salary.entity.Salary;
import com.selldok.toy.salary.entity.SalaryInfo;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    public SalaryResponse searchSalary(SalaryRequest salaryRequest){

        Salary salary = salaryRepository.findByOccupation(salaryRequest.getOccupation());
        SalaryInfo salaryInfo = salary.getSalaryInfo();
        SalaryResponse salaryResponse = new SalaryResponse(salary.getOccupation(),salaryInfo.getFirstSalary()
                                            ,salaryInfo.getSecondSalary(),salaryInfo.getThirdSalary()
                                            ,salaryInfo.getFourthSalary(),salaryInfo.getFifthSalary()
                                            ,salaryInfo.getSixthSalary(),salaryInfo.getSeventhSalary()
                                            ,salaryInfo.getEighthSalary(),salaryInfo.getNinthSalary()
                                            ,salaryInfo.getTenthSalary());
        return salaryResponse;
    }
}
