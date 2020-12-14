package com.selldok.toy.employee.service;

import java.util.Optional;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.employee.dao.AppliedCompanyRepository;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.AppliedCompany;
import com.selldok.toy.employee.entity.AppliedCompanyKey;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.AppliedCompanyDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author DongSeok,Kim
 */
@Service
@RequiredArgsConstructor
public class AppliedCompanyService {
	static Logger logger = LoggerFactory.getLogger(AppliedCompanyService.class);

	@Autowired
	private AppliedCompanyRepository acRepo;
	@Autowired
	private CompanyRepository cRepo;
	@Autowired
	private EmployeeRepository eRepo;

	public AppliedCompanyKey createOrUpdate(AppliedCompanyDto newAcDto) throws Exception {
		logger.debug("newAcDto={}", newAcDto);
		AppliedCompanyKey acKey;
		Optional<Employee> applicant = eRepo.findById(newAcDto.getApplicantId());
		Optional<Company> company = cRepo.findById(newAcDto.getCompanyId());
		if(applicant.isPresent()
			&& company.isPresent()) {
			acKey = new AppliedCompanyKey(newAcDto.getApplicantId(), newAcDto.getCompanyId());
			BasicInfo bi = BasicInfo.builder()
				.name(newAcDto.getName())
				.email(newAcDto.getEmail())
				.phoneNumber(newAcDto.getPhoneNumber())
				.build();
			AppliedCompany ac = AppliedCompany.builder()
				.appliedCompanyKey(acKey)
				.applicant(applicant.get())
				.info(bi)
				.appliedCompany(company.get())
				.build();
				acRepo.save(ac);
			acKey = ac.getAppliedCompanyKey();
		} else {
			throw new Exception("지원자 정보 혹은 회사 정보가 없습니다");
		}

		logger.debug("acKey={}", acKey);

		return acKey;
	}
}
