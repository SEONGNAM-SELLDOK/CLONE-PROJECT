package com.selldok.toy.employee.controller;

import java.util.HashMap;

import com.selldok.toy.employee.entity.AppliedCompanyKey;
import com.selldok.toy.employee.model.AppliedCompanyDto;
import com.selldok.toy.employee.service.AppliedCompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 지원이력
 * @author DongSeok, Kim
 */
@Controller
@RestController
@RequestMapping("appliedCompanies")
public class AppliedCompanyController {
	static Logger logger = LoggerFactory.getLogger(AppliedCompanyController.class);

	@Autowired
	private AppliedCompanyService acServ;

	/**
	 * 지원하기
	 * 
	 * @param newAcDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<HashMap<String, AppliedCompanyKey>> get(@RequestBody AppliedCompanyDto newAcDto) throws Exception {
		logger.debug("newAc={}", newAcDto);
		AppliedCompanyKey acKey = acServ.create(newAcDto);
		HashMap<String, AppliedCompanyKey> rtnMap = new HashMap<>();
		rtnMap.put("appliedCompanyKey", acKey);
		return new ResponseEntity<HashMap<String, AppliedCompanyKey>>(rtnMap, HttpStatus.OK);
	}
}
