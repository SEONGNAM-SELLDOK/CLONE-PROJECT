package com.selldok.toy.employee.service;

import java.util.HashMap;
import java.util.Map;
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
	private ApplyHistoryRepository applyHistoryRepository;
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
			applyHistoryRepository.save(applyHistory);
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
		Optional<ApplyHistory> existingApplyHistory = applyHistoryRepository.findById(updatingApplyHistoryDto.getId());
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
			
			BasicInfoBuilder updatingBasicInfoBuilder = BasicInfo.builder()
			.name(updatingApplyHistoryDto.getName())
			.email(updatingApplyHistoryDto.getEmail())
			.phoneNumber(updatingApplyHistoryDto.getPhoneNumber())
			;
			
			updatingApplyHistory.setBasicInfo(updatingBasicInfoBuilder.build());
			applyHistoryRepository.save(updatingApplyHistory);			
		} else {
			throw new Exception("존재하지 않는 지원이력입니다");
		}
	}

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격)
	 */
	public Map<String, Long> getApplyCount(Long applicantId) {
		Map<String, Long> applyCountList = new HashMap<String, Long>();
		//총 카운트
		applyCountList.put("allCnt", applyHistoryRepository.countByApplicantId(applicantId));
		//지원 완료 
		applyCountList.put("applcnComptCnt", applyHistoryRepository.countByStatusAndApplicantId(ApplyHistory.Status.APPLCN_COMPT, applicantId));
		//서류 통과
		applyCountList.put("papersPasageCnt", applyHistoryRepository.countByStatusAndApplicantId(ApplyHistory.Status.PAPERS_PASAGE, applicantId));
		//최종 합격
		applyCountList.put("lastPsexamCnt", applyHistoryRepository.countByStatusAndApplicantId(ApplyHistory.Status.LAST_PSEXAM, applicantId));
		//불합격
		applyCountList.put("dsqlfctCnt", applyHistoryRepository.countByStatusAndApplicantId(ApplyHistory.Status.DSQLFC, applicantId));
		log.debug("applyCountList={}", applyCountList);
		return applyCountList;
	}

	/**
	 * 상태 변경하기
	 */
	public void changeStatus(ApplyHistoryDto updatingApplyHistoryDto) {
		log.debug("updatingApplyHistoryDto={}", updatingApplyHistoryDto);
		Optional<ApplyHistory> existingApplyHistory = applyHistoryRepository.findById(updatingApplyHistoryDto.getId());
		existingApplyHistory.ifPresent(updatingApplyHistory -> {
            updatingApplyHistory.setStatus(updatingApplyHistoryDto.getStatus());
            applyHistoryRepository.save(updatingApplyHistory);
        });
	}
}
