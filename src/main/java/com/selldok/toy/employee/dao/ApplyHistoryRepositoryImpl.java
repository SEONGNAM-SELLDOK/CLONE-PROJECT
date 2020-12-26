package com.selldok.toy.employee.dao;

import static com.selldok.toy.employee.entity.QApplyHistory.applyHistory;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.model.QApplyHistoryDto;

/**
 * @author DongSeok,Kim
 */
public class ApplyHistoryRepositoryImpl implements ApplyHistoryRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public ApplyHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<ApplyHistoryDto> search(ApplyHistoryDto searchCondition) {
		return queryFactory
			.select(
				new QApplyHistoryDto(
					applyHistory.basicInfo.name
					,applyHistory.basicInfo.email
					,applyHistory.basicInfo.phoneNumber
					,applyHistory.employmentBoard.company.name
					,applyHistory.employmentBoard.title
					,applyHistory.appliedDt
					,applyHistory.status
				)
			)
			.from(applyHistory)
			.where(
				// where 코딩을 좀 더 깔끔하게 할 수 있는 방법이 없을까요?
				searchCondition.getName() != null ? applyHistory.basicInfo.name.contains(searchCondition.getName()) : null
				,searchCondition.getCompanyName() != null ? applyHistory.employmentBoard.company.name.contains(searchCondition.getCompanyName()) : null
				,searchCondition.getApplicantId() != null ? applyHistory.applicant.id.eq(searchCondition.getApplicantId()) : null
			)
			.offset(searchCondition.getOffset())
			.limit(searchCondition.getLimit())
			.orderBy(applyHistory.id.desc())
			.fetch();
	}
}
