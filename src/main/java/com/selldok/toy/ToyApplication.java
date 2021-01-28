package com.selldok.toy;

import javax.persistence.EntityManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.selldok.toy.reactiv")
@EnableJpaRepositories(basePackages = "com.selldok.toy.repository")
public class ToyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyApplication.class, args);
    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
