package com.selldok.toy.company.controller.web;

import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyCreateRequest;
import com.selldok.toy.company.service.CompanyWebfluxService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("webflux")
@RequiredArgsConstructor
public class CompanyHandler {

    private final CompanyWebfluxService service;

    public Mono<ServerResponse> findById(ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            return ServerResponse.ok().body(service.findById(Long.valueOf(id)), Company.class);
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().build();
        }
    }

    @PostMapping(path = "/company", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> create(final @Valid @RequestBody CompanyCreateRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Company company = Company.builder()
                .name(request.getName())
                .address(address)
                .businessNum(request.getBusinessNum())
                .totalSales(request.getTotalSales())
                .employees(request.getEmployees())
                .info(request.getInfo())
                .email(request.getEmail())
                .since(request.getSince())
                .phone(request.getPhone())
                .homepage(request.getHomepage())
                .terms(request.getTerms())
                .build();

        Mono<Long> just = Mono.just(service.create(company));

        return just;
    }

}
