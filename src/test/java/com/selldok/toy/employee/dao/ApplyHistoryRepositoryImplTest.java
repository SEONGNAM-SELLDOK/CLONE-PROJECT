package com.selldok.toy.employee.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.service.AppliedHistoryService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 김동석 2021.01.23
 */
@SpringBootTest
@Transactional
public class ApplyHistoryRepositoryImplTest {
	// gradle test 시 @Slf4j 찾을 수 없는 문제 발생하여 package lombok.extern.slf4j does not
	// exist
	// Logger 객체를 직접 가져오도록 함
	static Logger logger = LoggerFactory.getLogger(ApplyHistoryRepositoryImplTest.class);

	@Autowired
	ApplyHistoryRepositoryImpl applyHistoryRepositoryImpl;

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AppliedHistoryService appliedHistoryService;

	@Test
	public void searchTestNoData() {
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder().build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(0, applyHistoryDtoList.size());
	}

	@Test
	public void searchNameTest() {
		Employee newEmployee = employeeRepository.save(new Employee());
		Company newCompany = Company.builder()
		.name("회사명")
		.address(new Address("country", "city", "street"))
		.since("since")
		.businessNum("since")
		.phone("phone")
		.terms(true)
		.representative(newEmployee)
		.build()
		;
		companyRepository.save(newCompany);
		Board newBoard = Board.builder()
		.content("content")
		.image("image")
		.title("title")
		.company(newCompany)
		.build();
		newBoard.setCompany(newCompany);
		boardRepository.save(newBoard);

		ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.name("이름")
		.applicantId(newEmployee.getId())
		.employmentBoardId(newBoard.getId())
		.build();
		newApplyHistoryDto = appliedHistoryService.create(newApplyHistoryDto);

		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.name("이름")
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}	

	@Test
	public void searchCompanyNameTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.companyName("회사명")
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	@Test
	public void searchNameAndCompanyNameTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.name("이름")
		.companyName("회사명")
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	@Test
	public void applicantIdEqTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.applicantId(1L)
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	@Test
	public void companyIdEqTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.companyId(1L)
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	@Test
	public void statusEqTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.status(ApplyHistory.Status.APPLCN_COMPT)
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}			

	@Test
	public void reporesentativeCompanyIdEqTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.representativeId(1L)
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		
	
}
