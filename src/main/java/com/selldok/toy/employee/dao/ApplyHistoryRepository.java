package com.selldok.toy.employee.dao;

import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.ApplyHistory.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author DongSeok,Kim
 */
public interface ApplyHistoryRepository extends CrudRepository<ApplyHistory, Long> {
    Long countByStatusAndApplicantId(Status status, Long applicantId);
	Long countByApplicantId(Long applicantId);
    Page<ApplyHistory> findByBasicInfoNameContainsOrEmploymentBoardCompanyNameContains(Status applicantName, Status companyName, Pageable pageable);
}
