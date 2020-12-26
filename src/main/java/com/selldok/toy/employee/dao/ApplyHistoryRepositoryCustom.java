package com.selldok.toy.employee.dao;

import java.util.List;

import com.selldok.toy.employee.model.ApplyHistoryDto;

/**
 * @author DongSeok,Kim
 */
public interface ApplyHistoryRepositoryCustom {
    List<ApplyHistoryDto> search(ApplyHistoryDto searchCondition);
}
