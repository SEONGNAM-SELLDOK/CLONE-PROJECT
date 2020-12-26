package com.selldok.toy.employee.controller;

import java.util.List;
import java.util.Map;

import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.service.AppliedHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 지원이력 RestController
 * 
 * @author DongSeok, Kim
 */
@Slf4j
@RestController
public class ApplyHistoryController {
	@Autowired
	private AppliedHistoryService applyHistoryService;

	/**
	 * 지원하기
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("employees/{applicantId}/applyHistories")
	@ResponseBody
	public ResponseEntity<Long> create(@RequestBody ApplyHistoryDto applyHistoryDto, @PathVariable Long applicantId) throws Exception {
		applyHistoryDto.setApplicantId(applicantId);
		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<>(applyHistoryService.create(applyHistoryDto), HttpStatus.OK);
	}

	/**
	 * 수정하기
	 * 이 메소드를 쓰면 set 하지 않은 필드들은 null로 갱신되는 문제 있음
	 * 일부 컬럼만 갱신하는 기능 필요할까?
	 * 
	 * @param ApplyHistoryDto updatingApplyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@PutMapping("employees/{applicantId}/applyHistories/{id}")
	@ResponseBody
	public ResponseEntity update(@PathVariable Long id, @RequestBody ApplyHistoryDto updatingApplyHistoryDto, @PathVariable Long applicantId) throws Exception {
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
	@PutMapping("employees/{applicantId}/applyHistories/{id}/changeStatus")
	@ResponseBody
	public ResponseEntity changeStatus(@PathVariable Long id, @RequestBody ApplyHistoryDto updatingApplyHistoryDto, @PathVariable Long applicantId) throws Exception {
		updatingApplyHistoryDto.setId(id);
		updatingApplyHistoryDto.setApplicantId(applicantId);
		applyHistoryService.changeStatus(updatingApplyHistoryDto);
        return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격)
	 * http://localhost:8080/employees/1/applyHistories/getApplyCount
	 */
	@GetMapping("employees/{applicantId}/applyHistories/getApplyCount")
	@ResponseBody
	public ResponseEntity<Map<String, Long>> getApplyCount(@PathVariable Long applicantId) throws Exception {
        return new ResponseEntity<>(applyHistoryService.getApplyCount(applicantId), HttpStatus.ACCEPTED);
	}

	/**
	 * 검색
	 * http://localhost:9090/employees/1/applyHistories?name=%EC%A7%80%EC%9B%90&companyName=%EA%B5%AC
	 * 
	 * @param ApplyHistoryDto applyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("employees/{applicantId}/applyHistories")
	@ResponseBody
	public ResponseEntity<List<ApplyHistoryDto>> search(
		@PathVariable Long applicantId
		,@RequestParam(required=false) String name
		,@RequestParam(required=false) String companyName
		,@RequestParam(required=false, defaultValue="0") Long offset
		,@RequestParam(required=false, defaultValue="10") Long limit
	) throws Exception {
		ApplyHistoryDto applyHistoryDto = new ApplyHistoryDto(); 
		applyHistoryDto.setApplicantId(applicantId);
		applyHistoryDto.setName(name);
		applyHistoryDto.setCompanyName(companyName);
		applyHistoryDto.setOffset(offset);
		applyHistoryDto.setLimit(limit);
		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<>(applyHistoryService.search(applyHistoryDto), HttpStatus.ACCEPTED);
	}	
}
