package com.selldok.toy.employee.dao;

import java.util.List;

import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.ApplyHistory.Status;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author DongSeok,Kim
 */
public interface ApplyHistoryRepository extends CrudRepository<ApplyHistory, Long>, ApplyHistoryRepositoryCustom {
	Long countByStatusAndApplicantId(Status status, Long applicantId);
	Long countByApplicantId(Long applicantId);

	/**
	 * 지원 상태 카운트(전체, 지원 완료, 서류 통과, 최종 합격, 불합격)
	 * count가 0인 경우 select 자체가 안되는 문제가 있습니다. native query를 안쓰고 jpql 만으로 이 문제를 해결할 수 있을까요?
	 * 
	 * @param applicantId 지원자의 id
	 * @return
	 */
	@Query("SELECT applyHistory.status, COUNT(applyHistory.status) FROM ApplyHistory AS applyHistory Where applyHistory.applicant.id = :applicantId GROUP BY applyHistory.status")
	List<String[]> groupByCountByStatus(@Param("applicantId") Long applicantId);
}
