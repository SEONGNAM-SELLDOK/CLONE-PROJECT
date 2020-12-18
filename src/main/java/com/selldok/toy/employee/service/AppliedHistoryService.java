package com.selldok.toy.employee.service;

import java.util.Optional;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.employee.dao.ApplyHistoryRepository;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.ApplyHistoryDto;

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
public class AppliedHistoryService {
	@Autowired
	private ApplyHistoryRepository ApplyHistoryRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 신규 생성
	 */
	public Long create(ApplyHistoryDto newApplyHistoryDto) throws Exception {
		log.debug("newApplyHistoryDto={}", newApplyHistoryDto);
		Long newApplyHistoryId = null;
		Optional<Employee> applicant = employeeRepository.findById(newApplyHistoryDto.getApplicantId());
		Optional<Company> company = companyRepository.findById(newApplyHistoryDto.getCompanyId());
		if(applicant.isPresent()
			&& company.isPresent()) {
			BasicInfo memberBasicInfo = BasicInfo.builder()
																	.name(newApplyHistoryDto.getName())
																	.email(newApplyHistoryDto.getEmail())
																	.phoneNumber(newApplyHistoryDto.getPhoneNumber())
																	.build();
																	
			ApplyHistory applyHistory = ApplyHistory.builder()
																			.applicant(applicant.get())
																			.info(memberBasicInfo)
																			.appliedCompany(company.get())
																			.status(newApplyHistoryDto.getStatus())
																			.build();
			ApplyHistoryRepository.save(applyHistory);
			newApplyHistoryId = applyHistory.getId();
		} else {
			throw new Exception("지원자 정보 혹은 회사 정보가 없습니다");
		}
		log.debug("newApplyHistoryId={}", newApplyHistoryId);

		return newApplyHistoryId;
	}

	/**
	 * 갱신
	 */
	public void update(ApplyHistoryDto updatingApplyHistoryDto) throws Exception {
		log.debug("updatingApplyHistoryDto={}", updatingApplyHistoryDto);
		Optional<ApplyHistory> ApplyHistory = ApplyHistoryRepository.findById(updatingApplyHistoryDto.getId());
		Optional<Employee> applicant = employeeRepository.findById(updatingApplyHistoryDto.getApplicantId());
		Optional<Company> company = companyRepository.findById(updatingApplyHistoryDto.getCompanyId());
		if(applicant.isPresent()
			&& company.isPresent()) {
			BasicInfo memberBasicInfo = BasicInfo.builder()
																	.name(updatingApplyHistoryDto.getName())
																	.email(updatingApplyHistoryDto.getEmail())
																	.phoneNumber(updatingApplyHistoryDto.getPhoneNumber())
																	.build();
			ApplyHistory.ifPresent(existingApplyHistory -> {
				existingApplyHistory.setApplicant(applicant.get());
				existingApplyHistory.setAppliedCompany(company.get());
				existingApplyHistory.setInfo(memberBasicInfo);
				existingApplyHistory.setStatus(updatingApplyHistoryDto.getStatus());
				ApplyHistoryRepository.save(existingApplyHistory);
			});											
		} else {
			throw new Exception("지원자 정보 혹은 회사 정보가 없습니다");
		}
	}	
}
