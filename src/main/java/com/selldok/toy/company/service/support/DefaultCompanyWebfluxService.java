package com.selldok.toy.company.service.support;

import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.service.CompanyWebfluxService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DefaultCompanyWebfluxService implements CompanyWebfluxService {

    private final CompanyRepository companyRepository;

    @Override
    public Mono<Company> findById(Long id) {
        return Mono.defer(() -> Mono.justOrEmpty(companyRepository.findById(id)))
                .subscribeOn(Schedulers.newParallel("find-sub"));
    }

    @Override
    public Mono<List<Company>> findAllCompany() {
        return Mono.defer(() -> Mono.justOrEmpty(companyRepository.findAll()));
    }

    @Override
    public Mono<Long> create(Company company) {
        companyRepository.save(company);
        return Mono.defer(() -> Mono.justOrEmpty(company.getId()));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.just(id)
                .flatMap(i -> Mono.justOrEmpty(companyRepository.findById(i))
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "not found")))
                    .flatMap(u -> Mono.create(c -> companyRepository.delete(u)))
        );
    }
}
