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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 구인공고 지원 서비스
 * @author DongSeok,Kim
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppliedCompanyService {
	@Autowired
	private AppliedCompanyRepository appliedCompanyRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	public AppliedCompanyKey createOrUpdate(AppliedCompanyDto newAppliedCompanyDto) throws Exception {
		log.debug("newAppliedCompanyDto={}", newAppliedCompanyDto);
		AppliedCompanyKey appliedCompanyKey;
		Optional<Employee> applicant = employeeRepository.findById(newAppliedCompanyDto.getApplicantId());
		Optional<Company> company = companyRepository.findById(newAppliedCompanyDto.getCompanyId());
		if(applicant.isPresent()
			&& company.isPresent()) {
			appliedCompanyKey = new AppliedCompanyKey(newAppliedCompanyDto.getApplicantId(), newAppliedCompanyDto.getCompanyId());
			BasicInfo memberBasicInfo = BasicInfo.builder()
									.name(newAppliedCompanyDto.getName())
									.email(newAppliedCompanyDto.getEmail())
									.phoneNumber(newAppliedCompanyDto.getPhoneNumber())
									.build();
			AppliedCompany appliedCompany = AppliedCompany.builder()
											.appliedCompanyKey(appliedCompanyKey)
											.applicant(applicant.get())
											.info(memberBasicInfo)
											.appliedCompany(company.get())
											.status(newAppliedCompanyDto.getStatus())
											.build();
			appliedCompanyRepository.save(appliedCompany);
			appliedCompanyKey = appliedCompany.getAppliedCompanyKey();
		} else {
			throw new Exception("지원자 정보 혹은 회사 정보가 없습니다");
		}
		log.debug("appliedCompanyKey={}", appliedCompanyKey);

		return appliedCompanyKey;
	}
}
