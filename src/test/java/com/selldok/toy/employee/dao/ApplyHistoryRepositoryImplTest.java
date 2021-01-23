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
	// gradle test 시 @Slf4j 찾을 수 없는 문제 발생하여(package lombok.extern.slf4j does not exist)
	// Logger 객체를 LoggerFactory에서 가져오도록 함
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

	// 전체 메소드에서 사용하기 위해 임시 변수를 만듬
	Employee tempEmployee = null;
	Company tempCompany = null;
	Board tempBoard = null;

	/**
	 * 데이터가 없는 경우
	 */
	@Test
	public void searchTestNoData() {
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder().build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(0, applyHistoryDtoList.size());
	}

	/**
	 * 지원자 이름으로 검색(결과가 있는 경우)
	 */
	@Test
	public void searchNameTest() {
		tempEmployee = employeeRepository.save(new Employee());
		
		tempCompany = Company.builder()
		.name("회사명")
		.address(new Address("country", "city", "street"))
		.since("since")
		.businessNum("since")
		.phone("phone")
		.terms(true)
		.representative(tempEmployee)
		.build()
		;
		companyRepository.save(tempCompany);

		Board tempBoard = Board.builder()
		.content("content")
		.image("image")
		.title("title")
		.company(tempCompany)
		.build();
		tempBoard.setCompany(tempCompany);
		boardRepository.save(tempBoard);

		ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.name("이름")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
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

	/**
	 * 회사명으로 검색 테스트(결과가 있는 경우)
	 */
	@Test
	public void searchCompanyNameTest() {
		// 데이터를 만들기 위해 호출함. @BeforeEach 혹은 @BeforeAll 하지 않은 이유는 searchTestNoData() 에서는 불필요하기 때문
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.companyName("회사명")
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	/**
	 * 지원자 이름과 회사명으로 검색(결과가 있는 경우)
	 */
	@Test
	public void searchNameAndCompanyNameTest() {
		// 데이터를 만들기 위해 호출함. @BeforeEach 혹은 @BeforeAll 하지 않은 이유는 searchTestNoData() 에서는 불필요하기 때문
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

	/**
	 * 지원자 식별자로 검색(결과가 있는 경우)
	 */
	@Test
	public void applicantIdEqTest() {
		// 데이터를 만들기 위해 호출함. @BeforeEach 혹은 @BeforeAll 하지 않은 이유는 searchTestNoData() 에서는 불필요하기 때문
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.applicantId(tempEmployee.getId())
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	/**
	 * 회시 식별자로 검색(결과가 있는 경우)
	 */
	@Test
	public void companyIdEqTest() {
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.companyId(tempCompany.getId())
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		

	/**
	 * 지원 상태로 검색(결과가 있는 경우)
	 */
	@Test
	public void statusEqTest() {
		// 데이터를 만들기 위해 호출함. @BeforeEach 혹은 @BeforeAll 하지 않은 이유는 searchTestNoData() 에서는 불필요하기 때문
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.status(ApplyHistory.Status.APPLCN_COMPT)
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}			

	/**
	 * 회사 대표자 식별자로 검색(결과가 있는 경우)
	 */
	@Test
	public void reporesentativeCompanyIdEqTest() {
		// 데이터를 만들기 위해 호출함. @BeforeEach 혹은 @BeforeAll 하지 않은 이유는 searchTestNoData() 에서는 불필요하기 때문
		searchNameTest();
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
		.representativeId(tempEmployee.getId())
		.build();
		Pageable pageable = PageRequest.of(0, 10);
		List<ApplyHistoryDto> applyHistoryDtoList = applyHistoryRepositoryImpl.search(applyHistoryDto, pageable);
		Assertions.assertNotNull(applyHistoryDtoList);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		
	
}
