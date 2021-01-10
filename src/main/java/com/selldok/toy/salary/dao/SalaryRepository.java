package com.selldok.toy.salary.dao;

import com.selldok.toy.salary.entity.Salary;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Seil Park
 */
public interface SalaryRepository extends CrudRepository<Salary, Long> {
    Optional<Salary> findById(Long id);
}
