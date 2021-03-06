package com.selldok.toy.employee.controller;

import java.util.List;
import java.util.Map;

import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.service.AppliedHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 지원이력 RestController
 * 
 * @author DongSeok, Kim
 */
@Slf4j
@Controller
public class ApplyHistoryController {
	@Autowired
	private AppliedHistoryService applyHistoryService;

	/**
	 * 지원이력 조회
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("company/applications")
	public String companyApplications(Model model){
        //model.addAttribute("employee", response);
		return "company/applications";
	}
		
	/**
	 * 지원이력 조회
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("employees/applications")
	public String employeesApplications(Model model) {
        //model.addAttribute("employee", response);
		return "employee/applications";
	}
		
	/**
	 * 지원하기
	 * 
	 * @param applyHistoryDto
	 * @param applicantId
	 * @return
	 */
	@PostMapping("employees/{applicantId}/applyHistories")
	@ResponseBody
	public ResponseEntity<ApplyHistoryDto> create(@RequestBody ApplyHistoryDto applyHistoryDto, @PathVariable Long applicantId) {
		applyHistoryDto.setApplicantId(applicantId);
		return new ResponseEntity<>(applyHistoryService.create(applyHistoryDto), HttpStatus.OK);
	}

	/**
	 * 수정하기
	 * 이 메소드를 쓰면 set 하지 않은 필드들은 null로 갱신되는 문제 있음
	 * 일부 컬럼만 갱신하는 기능 필요할까?
	 * 
	 * @param id
	 * @param updatingApplyHistoryDto
	 * @param applicantId
	 * @return
	 */
	@PutMapping("employees/{applicantId}/applyHistories/{id}")
	@ResponseBody
	public ResponseEntity update(@PathVariable Long id, @RequestBody ApplyHistoryDto updatingApplyHistoryDto, @PathVariable Long applicantId) {
		updatingApplyHistoryDto.setId(id);
		updatingApplyHistoryDto.setApplicantId(applicantId);
		applyHistoryService.update(updatingApplyHistoryDto);
        return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	/**
	 * 상태 변경하기
	 * 
	 * @param ApplyHistoryDto updatingApplyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@PutMapping("applyHistories/{id}/changeStatus")
	@ResponseBody
	public ResponseEntity changeStatus(@PathVariable Long id, @RequestBody ApplyHistoryDto updatingApplyHistoryDto) throws Exception {
		updatingApplyHistoryDto.setId(id);
		applyHistoryService.changeStatus(updatingApplyHistoryDto);
        return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격)
	 * http://localhost:9090/employees/1/applyHistories/getApplyCount
	 */
	@GetMapping("employees/{applicantId}/applyHistories/getApplyCount")
	@ResponseBody
	public ResponseEntity<Map<String, Long>> groupByCountByStatusOfApplicant(@PathVariable Long applicantId) {
		return new ResponseEntity<>(applyHistoryService.groupByCountByStatusOfApplicant(applicantId), HttpStatus.OK);
	}

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격)
	 * http://localhost:9090/employees/1/applyHistories/getApplyCount
	 */
	@GetMapping("company/{companyId}/applyHistories/getApplyCount")
	@ResponseBody
	public ResponseEntity<Map<String, Long>> groupByCountByStatusOfCompany(@PathVariable Long companyId) {
		return new ResponseEntity<>(applyHistoryService.groupByCountByStatusOfCompany(companyId), HttpStatus.OK);
	}

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격
	 */
	@GetMapping("employees/{representativeId}/company/applyHistories/getApplyCount")
	@ResponseBody
	public ResponseEntity<Map<String, Long>> groupByCountByStatusOfRepresentativeCompany(@PathVariable Long representativeId) throws Exception {
		return new ResponseEntity<>(applyHistoryService.groupByCountByStatusOfRepresentativeCompany(representativeId), HttpStatus.OK);
	}	


	/**
	 * 개인별 지원이력 검색
	 * http://localhost:9090/employees/1/applyHistories?name=%EC%A7%80%EC%9B%90&companyName=%EA%B5%AC
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("employees/{applicantId}/applyHistories")
	@ResponseBody
	public ResponseEntity<List<ApplyHistoryDto>> employeesApplyHistoriesSearch(
		@PathVariable Long applicantId
		,@RequestParam(required=false) String name
		,@RequestParam(required=false) String companyName
		,@RequestParam(required=false) ApplyHistory.Status status
		,Pageable pageable
	) throws Exception {
		ApplyHistoryDto applyHistoryDto = ApplyHistoryDto.builder()
										.applicantId(applicantId)
										.name(name)
										.companyName(companyName)
										.status(status).build();

		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<>(applyHistoryService.search(applyHistoryDto, pageable), HttpStatus.OK);
	}	

	/**
	 * 회사별 지원이력 검색
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("company/{companyId}/applyHistories")
	@ResponseBody
	public ResponseEntity<List<ApplyHistoryDto>> companyApplyHistoriesSearch(
		@PathVariable Long companyId
		,@RequestParam(required=false) String name
		,@RequestParam(required=false) String companyName
		,@RequestParam(required=false) ApplyHistory.Status status
		,Pageable pageable
	) {
		ApplyHistoryDto applyHistoryDto = new ApplyHistoryDto(); 
		applyHistoryDto.setCompanyId(companyId);
		applyHistoryDto.setName(name);
		applyHistoryDto.setCompanyName(companyName);
		applyHistoryDto.setStatus(status);
		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<>(applyHistoryService.search(applyHistoryDto, pageable), HttpStatus.OK);
	}	

	/**
	 * 대표자 id로 회사별 지원이력 검색
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("employees/{representativeId}/company/applyHistories")
	@ResponseBody
	public ResponseEntity<List<ApplyHistoryDto>> representativeCompanyApplyHistoriesSearch(
		@PathVariable Long representativeId
		,@RequestParam(required=false) String name
		,@RequestParam(required=false) String companyName
		,@RequestParam(required=false) ApplyHistory.Status status
		,Pageable pageable
	) throws Exception {
		ApplyHistoryDto applyHistoryDto = new ApplyHistoryDto(); 
		applyHistoryDto.setRepresentativeId(representativeId);
		applyHistoryDto.setName(name);
		applyHistoryDto.setCompanyName(companyName);
		applyHistoryDto.setStatus(status);
		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<>(applyHistoryService.search(applyHistoryDto, pageable), HttpStatus.OK);
	}		
}
