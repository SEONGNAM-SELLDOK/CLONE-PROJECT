package com.selldok.toy.employee.dao;

import static com.selldok.toy.employee.entity.QApplyHistory.applyHistory;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.model.QApplyHistoryDto;

import org.springframework.data.domain.Pageable;

/**
 * @author DongSeok,Kim
 */
public class ApplyHistoryRepositoryImpl implements ApplyHistoryRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public ApplyHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<ApplyHistoryDto> search(ApplyHistoryDto searchCondition, Pageable pageable) {
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
				nameContains(searchCondition.getName())
				,companyNameContains(searchCondition.getCompanyName())
				,applicantIdEq(searchCondition.getApplicantId())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(applyHistory.id.desc())
			.fetch();
	}

	private BooleanExpression nameContains(String name) {
		return name != null ? applyHistory.basicInfo.name.contains(name) : null ;
	}
	private BooleanExpression companyNameContains(String companyName) {
		return companyName != null ? applyHistory.employmentBoard.company.name.contains(companyName) : null ;
	}
	private BooleanExpression applicantIdEq(Long applicantId) {
		return applicantId != null ? applyHistory.applicant.id.eq(applicantId) : null ;
	}
}
