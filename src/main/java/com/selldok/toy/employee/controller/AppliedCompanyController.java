package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.entity.AppliedCompanyKey;
import com.selldok.toy.employee.model.AppliedCompanyDto;
import com.selldok.toy.employee.service.AppliedCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 지원이력
 * @author DongSeok, Kim
 */
@Slf4j
@RestController
@RequestMapping("appliedCompanies")
public class AppliedCompanyController {
	@Autowired
	private AppliedCompanyService appliedCompanyServ;

	/**
	 * 지원하기
	 * 
	 * @param AppliedCompanyDto applyingCompanyDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<AppliedCompanyKey> createOrUpdate(@RequestBody AppliedCompanyDto applyingCompanyDto) throws Exception {
		log.debug("applyingCompanyDto={}", applyingCompanyDto);
		return new ResponseEntity<AppliedCompanyKey>(appliedCompanyServ.createOrUpdate(applyingCompanyDto), HttpStatus.OK);
	}

	/**
	 * 수정하기
	 * 이 메소드를 쓰면 set 하지 않은 필드들은 null로 갱신되는 문제 있음
	 * 일부 컬럼만 갱신하는 기능 필요할 듯.
	 * 
	 * @param AppliedCompanyDto updatingAppliedCompanyDto
	 * @return
	 * @throws Exception
	 */
	@PutMapping("")
	@ResponseBody
	public ResponseEntity<AppliedCompanyKey> update(@RequestBody AppliedCompanyDto updatingAppliedCompanyDto) throws Exception {
		return createOrUpdate(updatingAppliedCompanyDto);
	}
}
