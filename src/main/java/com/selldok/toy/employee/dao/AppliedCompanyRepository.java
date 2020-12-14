package com.selldok.toy.employee.dao;

import com.selldok.toy.employee.entity.AppliedCompany;
import com.selldok.toy.employee.entity.AppliedCompanyKey;

import org.springframework.data.repository.CrudRepository;

/**
 * @author DongSeok,Kim
 */
public interface AppliedCompanyRepository extends CrudRepository<AppliedCompany, AppliedCompanyKey> {
}
