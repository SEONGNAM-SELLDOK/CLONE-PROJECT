package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.service.AppliedHistoryService;

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
 * 지원이력 RestController
 * 
 * @author DongSeok, Kim
 */
@Slf4j
@RestController
@RequestMapping("applyHistories")
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
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Long> createOrUpdate(@RequestBody ApplyHistoryDto applyHistoryDto) throws Exception {
		log.debug("applyHistoryDto={}", applyHistoryDto);
		return new ResponseEntity<Long>(applyHistoryService.create(applyHistoryDto), HttpStatus.OK);
	}

	/**
	 * 수정하기
	 * 이 메소드를 쓰면 set 하지 않은 필드들은 null로 갱신되는 문제 있음
	 * 일부 컬럼만 갱신하는 기능 필요
	 * 
	 * @param ApplyHistoryDto updatingApplyHistoryDto
	 * @return
	 * @throws Exception
	 */
	@PutMapping("")
	@ResponseBody
	public ResponseEntity update(@RequestBody ApplyHistoryDto updatingApplyHistoryDto) throws Exception {
		applyHistoryService.update(updatingApplyHistoryDto);
        return new ResponseEntity(HttpStatus.ACCEPTED);
	}
}
