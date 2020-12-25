package com.selldok.toy.employee.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 * 검색 옵션
	 * 
	 * @author dskim
	 *
	 */
	public enum ComparisonOperator {
		FIRST // 처음 일치 ex) field like "aaaa%
		, EQ // 완전 일치 ex) field = "aaaa"
		, NEQ // 같지 않음 ex) field != "aaaa"
		, GT // greater than : >=
		, LT // less than : <=
		, LK // 부분 일치 field like "%aaaa%"
		;
	}

	/**
	 * 검색 옵션 조합 조건
	 */
	public enum Condition {
		AND
		, OR
	}

	@Lock(LockModeType.NONE)
	public List<T> findBy(List<String> schColumns, List<String> columnTypes, List<String> columnFormats,
			List<ComparisonOperator> comparisonOperators, List<String> schKeywords, List<Condition> conditions,
			Pageable pageable) throws Exception;

	@Lock(LockModeType.NONE)
	public Long countBy(List<String> schColumns, List<String> columnTypes, List<String> columnFormats,
			List<ComparisonOperator> comparisonOperators, List<String> schKeywords, List<Condition> conditions)
			throws Exception;
}
