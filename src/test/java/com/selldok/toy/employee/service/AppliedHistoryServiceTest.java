package com.selldok.toy.employee.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.employee.dao.ApplyHistoryRepository;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.ApplyHistory.Status;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.exception.RestApiException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 김동석
 * 2021.01.23
 */
@SpringBootTest
@Transactional
@Component
public class AppliedHistoryServiceTest {
	// gradle test 시 @Slf4j 찾을 수 없는 문제 발생하여 package lombok.extern.slf4j does not exist
	// Logger 객체를 직접 가져오도록 함
	static Logger logger = LoggerFactory.getLogger(AppliedHistoryServiceTest.class);

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AppliedHistoryService appliedHistoryService;
	@Autowired
	private ApplyHistoryRepository applyHistoryRepository;

	private Company tempCompany = null;
	private Board tempBoard = null;
	private Employee tempEmployee = null;

	@BeforeEach
	public void setup(){
		tempEmployee = employeeRepository.save(new Employee());

		tempCompany = Company.builder()
		.address(new Address("country", "city", "street"))
		.since("since")
		.businessNum("since")
		.phone("phone")
		.terms(true)
		.representative(tempEmployee)
		.build()
		;
		companyRepository.save(tempCompany);

		tempBoard = Board.builder()
		.content("content")
		.image("image")
		.title("title")
		.company(tempCompany)
		.build();
		tempBoard.setCompany(tempCompany);
		boardRepository.save(tempBoard);
	}

	/**
	 * 지원자 식별자에 해당하는 지원자가 없거나 구인게시물 식별자에 해당하는 게시물이 없으면 RestApiException 발생
	 */
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

	/**
	 * 구인게시물 식별자에 해당하는 게시물이 없으면 RestApiException 발생
	 */
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

	/**
	 * 입사 지원 성공
	 */
	@Test
	public void saveSuccess() {
		ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.name("지원자명")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
		.build();
		appliedHistoryService.create(newApplyHistoryDto);
		
		Assertions.assertTrue(newApplyHistoryDto.getId() > 0);
	}	

	/**
	 * 지원자 식별자에 해당하는 지원자가 없거나 구인게시물 식별자에 해당하는 게시물이 없으면 RestApiException 발생
	 */
	@Test
	public void updateWithEmplyNotFound() {
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

	/**
	 * 구인게시물 식별자에 해당하는 게시물이 없으면 RestApiException 발생
	 */
	@Test
	public void updateWithEmploymentBoardIdNotFound() {
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(tempEmployee.getId())
			.employmentBoardId(Long.MAX_VALUE)
			.id(1L)
			.build();
			appliedHistoryService.update(applyHistoryDto);
		});
	}

	/**
	 * 구직 이력 식별자에 해당하는 게시물이 없으면 RestApiException 발생
	 */
	@Test
	public void updateWithApplyHistoryNotFound() {
		assertThrows(RestApiException.class,
		()->{		
			ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
			.applicantId(tempEmployee.getId())
			.employmentBoardId(tempBoard.getId())
			.id(Long.MAX_VALUE)
			.build();
			appliedHistoryService.update(applyHistoryDto);
		});
	}

	/**
	 * 지원내용 변경 성공 테스트
	 */
	@Test
	public void updateSuccess() {	
		String newName = "변경된 이름";
		ApplyHistoryDto tempApplyHistoryDto = ApplyHistoryDto.builder()
		.name("원래 이름")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
		.build();
		tempApplyHistoryDto = appliedHistoryService.create(tempApplyHistoryDto);

		tempApplyHistoryDto.setName(newName);
		Boolean isSuccess = appliedHistoryService.update(tempApplyHistoryDto);
		Assertions.assertTrue(isSuccess);

		Optional<ApplyHistory> optionalApplyHistory = applyHistoryRepository.findById(tempApplyHistoryDto.getId());
		Assertions.assertTrue(optionalApplyHistory.isPresent());
		Assertions.assertEquals(newName, optionalApplyHistory.get().getBasicInfo().getName());
	}		

	/**
	 * 지원상태 변경 성공 테스트
	 */
	@Test
	public void changeStatusTest() {	
		Status newStatus = Status.CANCELED;
		ApplyHistoryDto tempApplyHistoryDto = ApplyHistoryDto.builder()
		.name("지원자명")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
		.build();
		tempApplyHistoryDto = appliedHistoryService.create(tempApplyHistoryDto);
		tempApplyHistoryDto.setStatus(newStatus);

		Boolean isSuccess = appliedHistoryService.changeStatus(tempApplyHistoryDto);
		Assertions.assertTrue(isSuccess);

		Optional<ApplyHistory> optionalApplyHistory = applyHistoryRepository.findById(tempApplyHistoryDto.getId());
		Assertions.assertTrue(optionalApplyHistory.isPresent());
		Assertions.assertEquals(newStatus, optionalApplyHistory.get().getStatus());
	}	

	@Test
	public void groupByCountByStatusOfApplicantTest() {
		ApplyHistoryDto tempApplyHistoryDto = ApplyHistoryDto.builder()
		.name("지원자명")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
		.build();
		tempApplyHistoryDto = appliedHistoryService.create(tempApplyHistoryDto);
		logger.debug("tempApplyHistoryDto={}", tempApplyHistoryDto);

		Map<String, Long> countByStatus = appliedHistoryService.groupByCountByStatusOfApplicant(tempApplyHistoryDto.getApplicantId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
		Assertions.assertEquals(countByStatus.get("APPLCN_COMPT"), Long.valueOf(1L));

		countByStatus = appliedHistoryService.groupByCountByStatusOfCompany(tempCompany.getId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
		Assertions.assertEquals(countByStatus.get("APPLCN_COMPT"), Long.valueOf(1L));

		countByStatus = appliedHistoryService.groupByCountByStatusOfRepresentativeCompany(tempCompany.getRepresentative().getId());
		Assertions.assertNotNull(countByStatus.get("APPLCN_COMPT"));
		Assertions.assertNotNull(countByStatus.get("PAPERS_PASAGE"));
		Assertions.assertNotNull(countByStatus.get("LAST_PSEXAM"));
		Assertions.assertNotNull(countByStatus.get("DSQLFC"));
		Assertions.assertNotNull(countByStatus.get("CANCELED"));
		Assertions.assertNotNull(countByStatus.get("allCount"));
		Assertions.assertEquals(countByStatus.get("APPLCN_COMPT"), Long.valueOf(1L));
	}

	@Test
	public void searchTest() {	
		ApplyHistoryDto tempApplyHistoryDto = ApplyHistoryDto.builder()
		.name("지원자명")
		.applicantId(tempEmployee.getId())
		.employmentBoardId(tempBoard.getId())
		.build();
		tempApplyHistoryDto = appliedHistoryService.create(tempApplyHistoryDto);
		logger.debug("tempApplyHistoryDto={}", tempApplyHistoryDto);

		Pageable pageable = PageRequest.of(0, 10);

		List<ApplyHistoryDto> applyHistoryDtoList = appliedHistoryService.search(tempApplyHistoryDto, pageable);
		Assertions.assertEquals(1, applyHistoryDtoList.size());
	}		
}