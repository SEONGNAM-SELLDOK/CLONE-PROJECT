package com.selldok.toy.employee.dao;

import com.selldok.toy.employee.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Incheol Jung
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
