package com.selldok.toy.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.selldok.toy.company.entity.QCompany.*;
import com.selldok.toy.company.model.CompanyListResponse;
import com.selldok.toy.company.model.QCompanyListResponse;
import static com.selldok.toy.employee.entity.QEmployee.*;
import static com.selldok.toy.employee.entity.QBasicInfo.*;
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
                    company.id,
                    company.name,
                    company.employees,
                    company.email,
                    company.phone,
                    company.homepage,
                    employee.id.as("employeeId"),
                    basicInfo.name.as("employeeName")))
                .from(company)
                .leftJoin(company.employee, employee)
                .fetch();

        return fetch;
    }

    @Override
    public Page<CompanyListResponse> searchPage(CompanySearchCondition condition, Pageable pageable) {
        QueryResults<CompanyListResponse> results = queryFactory
                .select(new QCompanyListResponse(
                        company.id,
                        company.name,
                        company.employees,
                        company.email,
                        company.phone,
                        company.homepage,
                        employee.id.as("employeeId"),
                        basicInfo.name.as("employeeName")))
                .from(company)
                .leftJoin(company.employee, employee)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CompanyListResponse> results1 = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(results1, pageable, total);
    }
}

