package com.selldok.toy.company.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
<<<<<<< HEAD
import static com.selldok.toy.company.entity.QCompany.*;
import static com.selldok.toy.company.entity.QMember.*;
=======
import com.selldok.toy.company.entity.QCompany;
import com.selldok.toy.company.entity.QMember;
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
import com.selldok.toy.company.model.CompanyListResponse;
import com.selldok.toy.company.model.QCompanyListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * @gogisung
 * */
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CompanyRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CompanyListResponse> search(CompanySearchCondition condition) {
        List<CompanyListResponse> fetch = queryFactory
                .select(new QCompanyListResponse(
<<<<<<< HEAD
                company.name,
                company.employees,
                company.email,
                company.phone,
                company.homepage,
                member.id.as("memberId"),
                member.name.as("memberName")))
                .from(company)
                .leftJoin(company.member, member)
=======
                QCompany.company.name,
                QCompany.company.employees,
                QCompany.company.email,
                QCompany.company.phone,
                QCompany.company.homepage,
                QMember.member.id.as("memberId"),
                QMember.member.name.as("memberName")))
                .from(QCompany.company)
                .leftJoin(QCompany.company.member, QMember.member)
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
                .fetch();

        return fetch;
    }

    @Override
    public Page<CompanyListResponse> searchPage(CompanySearchCondition condition, Pageable pageable) {
        QueryResults<CompanyListResponse> results = queryFactory
                .select(new QCompanyListResponse(
<<<<<<< HEAD
                        company.name,
                        company.employees,
                        company.email,
                        company.phone,
                        company.homepage,
                        member.id.as("memberId"),
                        member.name.as("memberName")))
                .from(company)
                .leftJoin(company.member, member)
=======
                        QCompany.company.name,
                        QCompany.company.employees,
                        QCompany.company.email,
                        QCompany.company.phone,
                        QCompany.company.homepage,
                        QMember.member.id.as("memberId"),
                        QMember.member.name.as("memberName")))
                .from(QCompany.company)
                .leftJoin(QCompany.company.member, QMember.member)
>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

<<<<<<< HEAD
=======

>>>>>>> 8e9b754d5648d87300beff5fa9d07f30eafd7263
        List<CompanyListResponse> results1 = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(results1, pageable, total);
    }
}

