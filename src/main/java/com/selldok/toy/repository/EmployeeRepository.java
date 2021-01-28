package com.selldok.toy.repository;

import com.selldok.toy.employee.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Incheol Jung
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByInfoEmail(String email);
}
