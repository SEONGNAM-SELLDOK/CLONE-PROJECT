package com.selldok.toy.employee.dao;

import static com.selldok.toy.employee.entity.QApplyHistory.applyHistory;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
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
				new QApplyHistoryDto(applyHistory.basicInfo.name)
			)
			.from(applyHistory)
			.where(
				// where 코딩을 좀 더 깔끔하게 할 수 있는 방법이 없을까요?
				searchCondition.getName() != null ? applyHistory.basicInfo.name.contains(searchCondition.getName()) : null
				,searchCondition.getCompanyName() != null ? applyHistory.employmentBoard.company.name.contains(searchCondition.getCompanyName()) : null
			)
			.fetch();
    }
}
