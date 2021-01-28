package com.selldok.toy.employee.dao;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.selldok.toy.employee.entity.Employee;

/**  * EmployeeR2dbcRepository
 *
 * @author incheol.jung
 * @since 2021. 01. 28.
 */
public interface EmployeeR2dbcRepository extends R2dbcRepository<Employee, Long> {
}
