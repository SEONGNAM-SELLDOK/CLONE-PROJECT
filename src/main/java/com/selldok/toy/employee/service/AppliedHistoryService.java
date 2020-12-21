package com.selldok.toy.employee.service;

import java.util.Optional;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.employee.dao.ApplyHistoryRepository;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.BasicInfo.BasicInfoBuilder;
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
	private BoardRepository boardRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 신규 생성
	 */
	public Long create(ApplyHistoryDto newApplyHistoryDto) throws Exception {
		log.debug("newApplyHistoryDto={}", newApplyHistoryDto);
		Long newApplyHistoryId = null;
		Optional<Employee> applicant = employeeRepository.findById(newApplyHistoryDto.getApplicantId());
		Optional<Board> employmentBoard = boardRepository.findById(newApplyHistoryDto.getEmploymentBoardId());
		if(applicant.isPresent()
			&& employmentBoard.isPresent()) {
			BasicInfo memberBasicInfo = BasicInfo.builder()
			.name(newApplyHistoryDto.getName())
			.email(newApplyHistoryDto.getEmail())
			.phoneNumber(newApplyHistoryDto.getPhoneNumber())
			.build();
			
			ApplyHistory applyHistory = ApplyHistory.builder()
			.applicant(applicant.get())
			.basicInfo(memberBasicInfo)
			.employmentBoard(employmentBoard.get())
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
		Optional<ApplyHistory> existingApplyHistory = ApplyHistoryRepository.findById(updatingApplyHistoryDto.getId());
		Optional<Employee> applicant = null;
		if(updatingApplyHistoryDto.getApplicantId() != null) {
			applicant = employeeRepository.findById(updatingApplyHistoryDto.getApplicantId());
			if(applicant.isEmpty()) {
				throw new Exception("존재하지 않는 구직자입니다");
			}
		}

		Optional<Board> employmentBoard = null;
		if(updatingApplyHistoryDto.getEmploymentBoardId() != null) {
			employmentBoard = boardRepository.findById(updatingApplyHistoryDto.getEmploymentBoardId());
			if(employmentBoard.isEmpty()) {
				throw new Exception("존재하지 않는 채용공고입니다");
			}
		}

		if(existingApplyHistory.isPresent()) {
			ApplyHistory updatingApplyHistory = existingApplyHistory.get();
			if(applicant != null && applicant.isPresent()) {
				updatingApplyHistory.setApplicant(applicant.get());
			}
			if(employmentBoard != null && employmentBoard.isPresent()) {
				updatingApplyHistory.setEmploymentBoard(employmentBoard.get());
			}
			//BasicInfo에 setter가 없음. 모델을 따로 만드는게 나을지, setter를 넣어달라고 하는게 맞을지  
			BasicInfo existingBasicInfo = updatingApplyHistory.getBasicInfo();
			BasicInfoBuilder updatingBasicInfoBuilder = BasicInfo.builder();
			
			if(updatingApplyHistoryDto.getName() != null) {
				updatingBasicInfoBuilder.name(updatingApplyHistoryDto.getName());
			} else {
				updatingBasicInfoBuilder.name(existingBasicInfo.getName());
			}

			if(updatingApplyHistoryDto.getEmail() != null) {
				updatingBasicInfoBuilder.email(updatingApplyHistoryDto.getEmail());
			} else {
				updatingBasicInfoBuilder.email(existingBasicInfo.getEmail());
			}

			if(updatingApplyHistoryDto.getPhoneNumber() != null) {
				updatingBasicInfoBuilder.phoneNumber(updatingApplyHistoryDto.getPhoneNumber());
			} else {
				updatingBasicInfoBuilder.phoneNumber(existingBasicInfo.getPhoneNumber());
			}
			
			if(updatingApplyHistoryDto.getStatus() != null) {
				updatingApplyHistory.setStatus(updatingApplyHistoryDto.getStatus());
			}
			updatingApplyHistory.setBasicInfo(updatingBasicInfoBuilder.build());

			ApplyHistoryRepository.save(updatingApplyHistory);			
		} else {
			throw new Exception("존재하지 않는 지원이력입니다");
		}
	}	
}
