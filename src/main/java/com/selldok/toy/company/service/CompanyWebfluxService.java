package com.selldok.toy.company.service;

import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyCreateRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompanyWebfluxService {

    Mono<Company> findById(Long id);

    Mono<List<Company>> findAllCompany();

    Long create(Company company);

    Mono<Void> delete(Long id);
}
