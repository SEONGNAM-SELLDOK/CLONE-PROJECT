package com.selldok.toy.repository;

import com.selldok.toy.employee.entity.PersonInfo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Incheol Jung
 */
public interface PersonInfoRepository extends CrudRepository<PersonInfo, Long> {
    Optional<PersonInfo> findByEmployeeId(Long employeeId);
}
