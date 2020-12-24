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
				new QApplyHistoryDto(applyHistory.applicant.info.name)
			)
			.from(applyHistory)
			.fetch();
    }
}
