package com.selldok.toy.employee.service;

import java.util.HashMap;
import java.util.List;
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
import com.selldok.toy.exception.ApplyErrorCode;
import com.selldok.toy.exception.RestApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
	 * 
	 * @param newApplyHistoryDto
	 * @return
	 */
	public Long create(ApplyHistoryDto newApplyHistoryDto) {
		log.debug("newApplyHistoryDto={}", newApplyHistoryDto);
		Long newApplyHistoryId = null;
		Optional<Employee> applicant = employeeRepository.findById(newApplyHistoryDto.getApplicantId());
		Optional<Board> employmentBoard = boardRepository.findById(newApplyHistoryDto.getEmploymentBoardId());

		if(applicant.isEmpty()) {
			throw new RestApiException(ApplyErrorCode.APY_001);
		} else if(employmentBoard.isEmpty()) {
			throw new RestApiException(ApplyErrorCode.APY_002);
		} else {
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
		}
		log.debug("newApplyHistoryId={}", newApplyHistoryId);

		return newApplyHistoryId;
	}

	/**
	 * 갱신
	 * 
	 * @param updatingApplyHistoryDto
	 */
	public void update(ApplyHistoryDto updatingApplyHistoryDto) {
		log.debug("updatingApplyHistoryDto={}", updatingApplyHistoryDto);
		Optional<ApplyHistory> existingApplyHistory = applyHistoryRepository.findById(updatingApplyHistoryDto.getId());
		Optional<Employee> applicant = null;
		if(updatingApplyHistoryDto.getApplicantId() != null) {
			applicant = employeeRepository.findById(updatingApplyHistoryDto.getApplicantId());
			if(applicant.isEmpty()) {
				throw new RestApiException(ApplyErrorCode.APY_001, HttpStatus.NOT_FOUND);
			}
		}

		Optional<Board> employmentBoard = null;
		if(updatingApplyHistoryDto.getEmploymentBoardId() != null) {
			employmentBoard = boardRepository.findById(updatingApplyHistoryDto.getEmploymentBoardId());
			if(employmentBoard.isEmpty()) {
				throw new RestApiException(ApplyErrorCode.APY_002, HttpStatus.NOT_FOUND);
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
			throw new RestApiException(ApplyErrorCode.APY_003, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 지원자 지원 현황 상태별 카운트리스트를 map으로 변환
	 * 
	 * @param applicantId 지원자의 아이디
	 * @return
	 */
	public Map<String, Long> groupByCountByStatusOfApplicant(Long applicantId) {
		List<String[]> groupByCountList = applyHistoryRepository.groupByCountByStatusApplicant(applicantId);
		return groupByCountListToMap(groupByCountList);
	}

	/**
	 * 회사 지원 현황 상태별 카운트리스트를 map으로 변환
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, Long> groupByCountByStatusOfCompany(Long companyId) {
		List<String[]> groupByCountList = applyHistoryRepository.groupByCountByStatusOfCompany(companyId);
		return groupByCountListToMap(groupByCountList);
	}

	/**
	 * 회사 지원 현황 상태별 카운트리스트를 map으로 변환
	 * 
	 * @param companyId
	 * @return
	 */	
	public Map<String, Long> groupByCountByStatusOfRepresentativeCompany(Long representativeId) {
		List<String[]> groupByCountList = applyHistoryRepository.groupByCountByStatusOfRepresentativeCompany(representativeId);
		return groupByCountListToMap(groupByCountList);
	}

	/**
	 * @param groupByCountList
	 * @return
	 */
	private Map<String, Long> groupByCountListToMap(List<String[]> groupByCountList) {
		//repository에서는 상태별로 group by count를 하므로 전체 카운트는 없음. 전체 카운트를 하는 것보다 일단은 상태별카운틀 합하도록 함
		long allCount = 0;

		Map<String, Long> groupByCountMap = new HashMap<>();
		for(String[] groupByCount : groupByCountList) {
			long tempGroupByCount = Long.parseLong(groupByCount[1]);
			allCount = allCount + tempGroupByCount;
			groupByCountMap.put(groupByCount[0], tempGroupByCount);
		}

		groupByCountMap.put("allCount", allCount);

		// count가 0인 경우 select 자체가 안되는 문제가 있어 카운트가 없다면 0으로 set
		for(ApplyHistory.Status status : ApplyHistory.Status.values()) {
			if(!groupByCountMap.containsKey(status.name())) {
				groupByCountMap.put(status.name(), 0L);
			}
		}
		return groupByCountMap;
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

	/**
	 * 검색
	 * @param pageable
	 * @param applyHistoryDto
	 * @return
	 */
	public List<ApplyHistoryDto> search(ApplyHistoryDto searchCondition, Pageable pageable) {
		return applyHistoryRepository.search(searchCondition, pageable);
	}
}
