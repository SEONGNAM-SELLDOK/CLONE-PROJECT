package com.selldok.toy.company.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.selldok.toy.company.entity.QBoard.*;
import static com.selldok.toy.company.entity.QCompany.*;

import com.selldok.toy.company.entity.Country;
import com.selldok.toy.company.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<BoardReadResponse> findByBoardId(Long id) {
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
        return StringUtils.hasText(businessNum) ? board.company.businessNum.eq(businessNum): null;
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? board.title.eq(title) : null;
    }

    private BooleanExpression endDateEq(LocalDate endDate) {
        return endDate != null ? board.endDate.eq(endDate) : null;
    }
}
