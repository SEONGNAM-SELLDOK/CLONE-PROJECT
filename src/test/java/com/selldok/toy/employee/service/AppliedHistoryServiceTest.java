package com.selldok.toy.employee.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.employee.dao.ApplyHistoryRepository;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.exception.RestApiException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**  * EventServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 15.
 */
@SpringBootTest
@Transactional
public class AppliedHistoryServiceTest {
	// gradle test 시 @Slf4j 찾을 수 없는 문제 발생하여 package lombok.extern.slf4j does not exist
	// Logger 객체를 직접 가져오도록 함
	static Logger logger = LoggerFactory.getLogger(AppliedHistoryServiceTest.class);

    @Autowired
	private ApplyHistoryRepository applyHistoryRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AppliedHistoryService appliedHistoryService;

	ApplyHistoryDto newApplyHistoryDto;
	Board newBoard;
	Employee newEmployee;

	@Test
	public void saveWithEmplyNotFound() {
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(999L)
			.employmentBoardId(999L)
			.build();
			appliedHistoryService.create(newApplyHistoryDto);
		});
	}

	@Test
	public void saveWithEmploymentBoardIdNotFound() {
		Employee newEmployee = employeeRepository.save(new Employee());
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(newEmployee.getId())
			.employmentBoardId(999L)
			.build();
			appliedHistoryService.create(newApplyHistoryDto);
		});
	}

	@Test
	public void saveSuccess() {
		Employee newEmployee = employeeRepository.save(new Employee());
		this.newEmployee = newEmployee;
		Company newCompany = Company.builder()
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
		this.newBoard = newBoard;

		ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.applicantId(newEmployee.getId())
		.employmentBoardId(newBoard.getId())
		.build();
		this.newApplyHistoryDto = appliedHistoryService.create(newApplyHistoryDto);
		
		Assertions.assertTrue(newApplyHistoryDto.getId() > 0);
	}	

	@Test
	public void updateWithEmplyNotFound() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(999L)
			.employmentBoardId(999L)
			.id(1L)
			.build();
			appliedHistoryService.update(newApplyHistoryDto);
		});
	}	

	@Test
	public void updateWithEmploymentBoardIdNotFound() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(this.newEmployee.getId())
			.employmentBoardId(999L)
			.id(1L)
			.build();
			appliedHistoryService.update(newApplyHistoryDto);
		});
	}

	@Test
	public void updateWithApplyHistoryNotFound() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(this.newEmployee.getId())
			.employmentBoardId(this.newBoard.getId())
			.id(Long.MAX_VALUE)
			.build();
			appliedHistoryService.update(newApplyHistoryDto);
		});
	}

	@Test
	public void updateSuccess() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.applicantId(this.newEmployee.getId())
		.employmentBoardId(this.newBoard.getId())
		.id(1L)
		.build();
		Boolean isSuccess = appliedHistoryService.update(this.newApplyHistoryDto);
		Assertions.assertTrue(isSuccess);
	}		

	@Test
	public void changeStatusTest() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		Boolean isSuccess = appliedHistoryService.changeStatus(this.newApplyHistoryDto);
		Assertions.assertTrue(isSuccess);
	}	

	@Test
	public void groupByCountByStatusOfApplicantTest() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		Map<String, Long> countByStatus = appliedHistoryService.groupByCountByStatusOfApplicant(this.newApplyHistoryDto.getId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
	}

	@Test
	public void groupByCountByStatusOfCompanyTest() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		Map<String, Long> countByStatus = appliedHistoryService.groupByCountByStatusOfCompany(this.newApplyHistoryDto.getId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
	}

	@Test
	public void groupByCountByStatusOfRepresentativeCompanyTest() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		Map<String, Long> countByStatus = appliedHistoryService.groupByCountByStatusOfRepresentativeCompany(this.newApplyHistoryDto.getId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
	}	

	@Test
	public void searchTest() {
		saveSuccess();
		logger.debug("this.newApplyHistoryDto={}", this.newApplyHistoryDto);
		logger.debug("this.newEmployee={}", this.newEmployee);
	
		Pageable pageable = PageRequest.of(0, 10);

		List<ApplyHistoryDto> applyHistoryDtoList = appliedHistoryService.search(this.newApplyHistoryDto, pageable);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		
}