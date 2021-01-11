package com.selldok.toy.company.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.selldok.toy.company.entity.QBoard.*;
import static com.selldok.toy.company.entity.QCompany.*;

import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @gogisung
 * */
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<BoardReadResponse> findByBoardInfo(Long id) {
        return queryFactory
                .select(new QBoardReadResponse(
                    board.title,
                    board.content,
                    board.image,
                    board.endDate,
                    company.name.as("companyName"),
                    company.address.country.as("companyCountry"),
                    company.address.city.as("companyCity"),
                    company.address.street.as("companyStreet")))
                .from(board)
                .leftJoin(board.company, company)
                .where(
                        company.id.eq(board.company.id),
                        board.id.eq(id)

                )
                .fetch();
    }

    @Override
    public List<NewHireListResponse> newHireByBoardInfo() {
        return queryFactory
                .select(new QNewHireListResponse(
                        company.id,
                        board.image,
                        company.name.as("companyName")))
                .from(board)
                .leftJoin(board.company, company)
                .orderBy(company.id.desc())
                .limit(4)
                .fetch();
    }

    @Override
    public List<RecommendThisWeekResponse> recommendThisWeek() {
        return queryFactory
                .select(new QRecommendThisWeekResponse(
                    board.id,
                    board.title,
                    company.name.as("companyName"),
                    company.address.country.as("companyCountry"),
                    company.address.city.as("companyCity"),
                    board.image))
                .from(board)
                .leftJoin(board.company, company)
                .orderBy(board.recommendation.desc())
                .fetch();
    }

    @Override
    public Page<BoardListResponse> searchBoard(BoardSearchCondition condition, Pageable pageable) {

        QueryResults<BoardListResponse> results = queryFactory
                .select(new QBoardListResponse(
                        board.id,
                        board.title,
                        board.image,
                        company.name.as("companyName"),
                        company.address.country.as("companyCountry"),
                        company.address.city.as("companyCity")))
                .from(board)
                .leftJoin(board.company, company)
                .where(
                        company.id.eq(board.company.id),
                        companyNameEq(condition.getCompanyName()),
                        businessNumEq(condition.getBusinessNum()),
                        companyCityEq(condition.getCity()),
                        companyCountryEq(condition.getCountry()),
                        titleEq(condition.getTitle()),
                        endDateEq(condition.getEndDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardListResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression companyNameEq(String companyName) {
        return StringUtils.hasText(companyName) ? board.company.name.eq(companyName) : null;
    }

    private BooleanExpression businessNumEq(String businessNum) {
        return StringUtils.hasText(businessNum) ? board.company.businessNum.eq(businessNum) : null;
    }

    private BooleanExpression companyCityEq(String companyCity) {
        return StringUtils.hasText(companyCity) ? board.company.address.city.eq(companyCity) : null;
    }

    private BooleanExpression companyCountryEq(String companyCountry) {
        return StringUtils.hasText(companyCountry) ? board.company.address.country.eq(companyCountry) : null;
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? board.title.eq(title) : null;
    }

    private BooleanExpression endDateEq(Date endDate) {
        return endDate != null ? board.endDate.eq(endDate) : null;
    }
}
