package com.selldok.toy.employee.dao;

import static com.selldok.toy.employee.entity.QApplyHistory.applyHistory;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selldok.toy.company.entity.QAddress;
import com.selldok.toy.employee.entity.ApplyHistory;
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
					applyHistory.id
					,applyHistory.basicInfo.name
					,applyHistory.basicInfo.email
					,applyHistory.basicInfo.phoneNumber
					,applyHistory.employmentBoard.company.name
					,applyHistory.employmentBoard.id
					,applyHistory.employmentBoard.title
					,applyHistory.appliedDt
					,applyHistory.status
				)
			)
			.from(applyHistory)
			.where(
				containsOr(searchCondition.getName(), searchCondition.getCompanyName())
				,applicantIdEq(searchCondition.getApplicantId())
				,statusEq(searchCondition.getStatus())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(applyHistory.id.desc())
			.fetch();
	}

	private Expression<String> getCompanyCountry(QAddress address) {
		//return address == null ? null : address.country.coalesce("");
		return address.country.coalesce("");
	}

	/**
	 * 지원자명과 회사명이 둘 다 넘어오는 경우 or 검색을 수행 함
	 */
	private BooleanExpression containsOr(String name, String companyName) {
		BooleanExpression contains = null;
		BooleanExpression nameContains = nameContains(name);
		BooleanExpression companyNameContains = companyNameContains(companyName);
		if(nameContains == null && companyNameContains != null) {
			contains = companyNameContains;
		} else if(nameContains != null && companyNameContains == null) {
			contains = nameContains;
		} else if(nameContains != null) {
			contains = nameContains.or(companyNameContains);
		}
		return contains;
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
	private BooleanExpression statusEq(ApplyHistory.Status status) {
		return status != null ? applyHistory.status.eq(status) : null ;
	}
}
