package com.selldok.toy.salary.service;

import com.selldok.toy.salary.entity.Occupation;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.model.SalaryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SalaryServiceTest {

    @Autowired
    private SalaryService salaryService;

    /**
     * prev() 실행이 되었기 때문에 이를 가지고 test
     */
    @Test
    void getSalaryByIdTest() {
        SalaryResponse expectedResponse = SalaryResponse.builder()
                .occupation(Occupation.dotNet)
                .yearOne(1000).yearTwo(2000).yearThree(3000).yearFour(4000).yearFive(5000)
                .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build();

        SalaryResponse salaryResponse = salaryService.getSalaryById(1L);

        assertTrue(isAllEquals(expectedResponse,salaryResponse));
    }

    private boolean isAllEquals(SalaryResponse expectedResponse, SalaryResponse salaryResponse) {
        return (expectedResponse.getOccupation() == salaryResponse.getOccupation() && expectedResponse.getYearOne() == salaryResponse.getYearOne()
        && expectedResponse.getYearTwo()==salaryResponse.getYearTwo() && expectedResponse.getYearThree() ==salaryResponse.getYearThree() && expectedResponse.getYearFour() == salaryResponse.getYearFour()
        && expectedResponse.getYearFive() == salaryResponse.getYearFive() && expectedResponse.getYearSix() == salaryResponse.getYearSix() && expectedResponse.getYearSeven() == salaryResponse.getYearSeven()
        && expectedResponse.getYearEight() ==salaryResponse.getYearEight() && expectedResponse.getYearNine() == salaryResponse.getYearNine() && expectedResponse.getYearTen() == salaryResponse.getYearTen());
    }

    @Test
    void updateSalaryTest() {
        SalaryRequest salaryRequest = SalaryRequest.builder()
                .yearOne(1000).yearTwo(2000).yearThree(3000).yearFour(4000).yearFive(5000)
                .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build();

        SalaryResponse expectedAnswer =  SalaryResponse.builder().
                occupation(Occupation.java).yearOne(1000).yearTwo(2000).yearThree(3000).yearFour(4000).yearFive(5000)
                .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build();

        salaryService.updateSalary(2L,salaryRequest);

        SalaryResponse salaryResponse = salaryService.getSalaryById(2L);

        assertTrue(isAllEquals(expectedAnswer,salaryResponse));

    }
}