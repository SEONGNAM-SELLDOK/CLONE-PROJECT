package com.selldok.toy.employee.dao;

import com.selldok.toy.employee.entity.ApplyHistory;
import com.selldok.toy.employee.entity.ApplyHistory.Status;

import org.springframework.data.repository.CrudRepository;

/**
 * @author DongSeok,Kim
 */
public interface ApplyHistoryRepository extends CrudRepository<ApplyHistory, Long>, ApplyHistoryRepositoryCustom {
    Long countByStatusAndApplicantId(Status status, Long applicantId);
	Long countByApplicantId(Long applicantId);
}
