package com.selldok.toy.employee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

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

import reactor.core.publisher.Mono;

/**
 * 구인공고 지원 서비스
 * @author DongSeok,Kim
 */

@Slf4j
@Service
public class AppliedHistoryWebfluxService  {
	@Autowired
	private ApplyHistoryRepository applyHistoryRepository;

	public Mono<Map<String, Long>> groupByCountByStatusOfApplicantWebFlux(Long applicantId) {
		log.debug("groupByCountByStatusOfApplicantWebFlux={}", applicantId);
		return Mono.defer(() -> Mono.justOrEmpty(groupByCountByStatusOfApplicant(applicantId)));
	}

	/*
	public Map<String, Long> groupByCountByStatusOfApplicantWebFlux(Long applicantId) {
		log.debug("groupByCountByStatusOfApplicantWebFlux={}", applicantId);
		return groupByCountByStatusOfApplicant(applicantId);
	}
	*/

	/**
	 * 지원자 지원 현황 상태별 카운트리스트를 map으로 변환
	 * 
	 * @param applicantId 지원자의 아이디
	 * @return
	 */
	public Map<String, Long> groupByCountByStatusOfApplicant(Long applicantId) {
		log.debug("groupByCountByStatusOfApplicant={}", applicantId);
		List<String[]> groupByCountList = applyHistoryRepository.groupByCountByStatusApplicant(applicantId);
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
}
