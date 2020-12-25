package com.selldok.toy.employee.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.StringUtils;

/**
 * 모든 repo에 공통으로 사용할 메소드들
 * 
 * @author dskim
 *
 * @param <T>
 * @param <ID>
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {
	static Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	private EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	private CriteriaQuery setCriteriaQuery(
		Root<T> root
		, CriteriaQuery  cQuery
		, CriteriaBuilder builder
		, List<String> schColumns
		, List<String> columnTypes
		, List<String> columnFormats
		, List<ComparisonOperator> comparisonOperators
		, List<String> schKeywords
		, List<Condition> conditions) throws Exception {
		Predicate concatPredicate = null;

		logger.debug("schColumns={}, columnTypes={}, columnFormats={}, comparisonOperators={}, schKeywords={}, conditions={}"
													, schColumns, columnTypes, columnFormats, comparisonOperators, schKeywords, conditions);
		if(schColumns != null) {
			logger.debug("schColumns.size()={}, columnTypes.size()={}, columnFormats.size()={}, comparisonOperators.size()={}, schKeywords.size()={}, conditions.size()={}"
														, schColumns.size(), columnTypes.size(), columnFormats.size(), comparisonOperators.size(), schKeywords.size(), conditions.size());
		}

		if(schColumns != null
			&& columnTypes != null 
			&& columnFormats != null
			&& comparisonOperators != null
			&& schKeywords != null
			&& conditions != null
			&& schColumns.size() == columnTypes.size()
			&& columnTypes.size() == columnFormats.size()
			&& columnFormats.size() == comparisonOperators.size()
			&& comparisonOperators.size() == schKeywords.size()
			&& schKeywords.size() == conditions.size()
		) {			
			for(int i = 0 ; i < schColumns.size() ; i++) {
				Predicate findBy = null;
				if(StringUtils.hasText(schColumns.get(i)) 
					&& StringUtils.hasText(schKeywords.get(i)) 
					&& comparisonOperators.get(i) != null) {			
						switch (comparisonOperators.get(i)) {
							case FIRST :
								findBy = builder.like(root.<String>get(schColumns.get(i)), schKeywords.get(i) + "%");
								break;
							case EQ :
								findBy = getFindByTemporal(builder, root, schColumns.get(i), schKeywords.get(i), columnTypes.get(i), columnFormats.get(i), comparisonOperators.get(i));
								if(findBy == null) {
									switch(columnTypes.get(i)) {
										case "BOOLEAN" :
											findBy = builder.equal(root.<Boolean>get(schColumns.get(i)), Boolean.parseBoolean(schKeywords.get(i)));
											break;
										case "NUMBER" :
											findBy = builder.equal(root.<Double>get(schColumns.get(i)), Double.parseDouble(schKeywords.get(i)));
											break;
										case "STRING" :
											findBy = builder.equal(root.<String>get(schColumns.get(i)), schKeywords.get(i));
											break;
										default :
											//findBy = builder.notEqual(root.get(schColumns.get(i)), Class.forName("com.kware.dis.jpa.dis.domain.ElctrnDsnfCerfin$Status.DELETE"));
											//findBy = builder.equal(root.get(schColumns.get(i)), getEnumOfString(Class.forName("com.kware.dis.jpa.dis.domain.ElctrnDsnfCerfin$Status"), schKeywords.get(i)));
											findBy = builder.equal(root.get(schColumns.get(i)), getEnumOfString(Class.forName(columnTypes.get(i)), schKeywords.get(i)));
											break;
									}
								}
								break;
							case NEQ :
								findBy = getFindByTemporal(builder, root, schColumns.get(i), schKeywords.get(i), columnTypes.get(i), columnFormats.get(i), comparisonOperators.get(i));
								switch(columnTypes.get(i)) {
									case "BOOLEAN" :
										findBy = builder.notEqual(root.<Boolean>get(schColumns.get(i)), Boolean.parseBoolean(schKeywords.get(i)));
										break;
									case "NUMBER" :
										findBy = builder.notEqual(root.<Double>get(schColumns.get(i)), Double.parseDouble(schKeywords.get(i)));
										break;
									case "STRING" :
										findBy = builder.notEqual(root.<String>get(schColumns.get(i)), schKeywords.get(i));
										break;
									default :
										findBy = builder.notEqual(root.get(schColumns.get(i)), getEnumOfString(Class.forName(columnTypes.get(i)), schKeywords.get(i)));
										break;
								}
								break;
							case GT :
								findBy = getFindByTemporal(builder, root, schColumns.get(i), schKeywords.get(i), columnTypes.get(i), columnFormats.get(i), comparisonOperators.get(i));
								if(findBy == null) {
									switch(columnTypes.get(i)) {
										case "NUMBER" :
											findBy = builder.greaterThanOrEqualTo(root.<Double>get(schColumns.get(i)), Double.parseDouble(schKeywords.get(i)));
											break;
										case "STRING" :
											findBy = builder.greaterThanOrEqualTo(root.<String>get(schColumns.get(i)), schKeywords.get(i));
											break;
									}			
								}					
								break;
							case LT :
								findBy = getFindByTemporal(builder, root, schColumns.get(i), schKeywords.get(i), columnTypes.get(i), columnFormats.get(i), comparisonOperators.get(i));
								if(findBy == null) {
									switch(columnTypes.get(i)) {
										case "NUMBER" :
											findBy = builder.lessThanOrEqualTo(root.<Double>get(schColumns.get(i)), Double.parseDouble(schKeywords.get(i)));
											break;
										case "STRING" :
											findBy = builder.lessThanOrEqualTo(root.<String>get(schColumns.get(i)), schKeywords.get(i));
											break;
									}												
								}
								break;
							case LK :
								findBy = builder.like(root.<String>get(schColumns.get(i)), "%" + schKeywords.get(i)  + "%");
								break;
					}			
				}

				if(findBy != null) {
					logger.debug("findBy={} conditions.get({}})={}", findBy, i, conditions.get(i));
					switch(conditions.get(i)) {
						case AND:
							if(concatPredicate == null) {
								concatPredicate = findBy;
							} else {
								concatPredicate = builder.and(concatPredicate, findBy);
							}
							break;
						case OR:
							if(concatPredicate == null) {
								concatPredicate = findBy;
							} else {
								concatPredicate = builder.or(concatPredicate, findBy);
							}
							break;
					}
				}
			}
		}

		logger.debug("concatPredicate={}", concatPredicate);

		CriteriaQuery rtn = cQuery;
		if(concatPredicate != null) {
			rtn = cQuery.where(concatPredicate); 
		}
		return rtn;
	}

	/**
	 * enum 에서 해당값에 해당하는 객체 찾기
	 * 순차검색이기 때문에 효율이 좋지 않음 -.-
	*/
	private Object getEnumOfString(Class theEnum, String enumValue) {
		Object[] candidateArr = theEnum.getEnumConstants();
		Object foundEnum = null;
		for(Object candidate : candidateArr) {
			logger.debug(" \t candidate={}, stringCompare={}", candidate, candidate.toString().equals(enumValue));
			if(candidate.toString().equals(enumValue)) {
				foundEnum = candidate;
				logger.debug(" \t \t foundEnum={}", foundEnum);
				break;
			}
    }
		return foundEnum;
	}

	private Predicate getFindByTemporal(CriteriaBuilder builder, Root<T> root, String schColumn, String schKeyword, String columnType, String columnFormat, ComparisonOperator comparisonOperator) throws ParseException {
		Predicate findByTemporal = null;
		if(columnType.equals("DATE") || columnType.equals("TIME")) {
			SimpleDateFormat format = new SimpleDateFormat(columnFormat);
			logger.debug("temporal schKeyword={} columnFormat={}, parsed={}", schKeyword, columnFormat, format.parse(schKeyword));
			switch (comparisonOperator) {
				case EQ :
					findByTemporal = builder.equal(root.<java.sql.Date>get(schColumn), format.parse(schKeyword));				
					break;
				case NEQ :
					findByTemporal = builder.notEqual(root.<java.sql.Date>get(schColumn), format.parse(schKeyword));				
					break;
				case GT :
					findByTemporal = builder.greaterThanOrEqualTo(root.<java.sql.Date>get(schColumn), format.parse(schKeyword));				
					break;
				case LT :
					findByTemporal = builder.lessThanOrEqualTo(root.<java.sql.Date>get(schColumn), format.parse(schKeyword));				
					break;
			}						
		}		
		return findByTemporal;
	}
	/**
	 * 모든 repo에 공통으로 사용할 검색쿼리용
	 * 
	 * @throws ParseException
	 */
	@Override
	public List<T> findBy(
			List<String> schColumns
		, List<String> columnTypes
		, List<String> columnFormats
		, List<ComparisonOperator> comparisonOperators
		, List<String> schKeywords
		, List<Condition> conditions
		, Pageable pageable
	) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
		Root<T> root = cQuery.from(getDomainClass());
		cQuery.select(root);		

		setCriteriaQuery(root, cQuery, builder,  schColumns, columnTypes, columnFormats, comparisonOperators, schKeywords, conditions);
    	
		Sort sort = pageable.getSort();
		List<Order> sortList = sort.toList();	// 불필요한 코드?
		for(Sort.Order order : sortList) {	    	
			logger.debug("order={}", order);
			//cQuery.orderBy(o);
			//cQuery.orderBy();
			//builder.asc(root.get(order));	    	
			
			Direction dir = order.getDirection();
			switch (dir) {
				case ASC :
					cQuery.orderBy(builder.asc(root.get(order.getProperty())));
					break;
				case DESC :
					cQuery.orderBy(builder.desc(root.get(order.getProperty())));
					break;
			}
		}
			
		TypedQuery<T> query = entityManager.createQuery(cQuery);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}

	/**
	 * 모든 repo에 공통으로 사용할 count쿼리용
	 */
	@Override
	public Long countBy(
		List<String> schColumns
		, List<String> columnTypes
		, List<String> columnFormats
		, List<ComparisonOperator> comparisonOperators
		, List<String> schKeywords
		, List<Condition> conditions		
	) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<T> root = cQuery.from(getDomainClass());
		cQuery.select(builder.count(root));

		setCriteriaQuery(root, cQuery, builder,  schColumns, columnTypes, columnFormats, comparisonOperators, schKeywords, conditions);

	  return entityManager.createQuery(cQuery).getSingleResult();
	}	
}