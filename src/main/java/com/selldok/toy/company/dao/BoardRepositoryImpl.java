package com.selldok.toy.company.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selldok.toy.company.entity.QBoard;
import com.selldok.toy.company.entity.QCompany;
import com.selldok.toy.company.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public List<BoardReadResponse> read(Long id) {
        return queryFactory
                .select(new QBoardReadResponse(
                QBoard.board.title,
                QBoard.board.content,
                QBoard.board.image,
                QBoard.board.endDate,
                QCompany.company.name.as("companyName"),
                QCompany.company.address.country.as("companyCountry"),
                QCompany.company.address.city.as("companyCity"),
                QCompany.company.address.street.as("companyStreet")))
                .from(QBoard.board)
                .leftJoin(QBoard.board.company, QCompany.company)
                .where(
                        QCompany.company.id.eq(QBoard.board.company.id),
                        QBoard.board.id.eq(id)
                )
                .fetch();
    }

    @Override
    public Page<BoardListResponse> searchBoardPage(Pageable pageable) {
        QueryResults<BoardListResponse> results = queryFactory
                .select(new QBoardListResponse(
                        QBoard.board.title,
                        QBoard.board.image,
                        QCompany.company.name.as("companyName"),
                        QCompany.company.address.country.as("companyCountry"),
                        QCompany.company.address.city.as("companyCity")))
                .from(QBoard.board)
                .leftJoin(QBoard.board.company, QCompany.company)
                .where(
                        QCompany.company.id.eq(QBoard.board.company.id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardListResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
