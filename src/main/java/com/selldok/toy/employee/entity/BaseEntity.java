package com.selldok.toy.employee.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

/**
 * 모든 도메인  객체들의 부모 객체
 * @author dskim
 */
@Data
//@Embeddable
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -6163194648909369778L;
	
	/**
	 * 컬럼 유형
	 * @author dskim
	 *
	 */
	public enum ColumnType
	{ 
		STRING				// varchar
			,NUMBER					// 수치
			,DATE			// 날짜
			,TIME		// 시각
			,BOOLEAN		// 시각
			;
	} 

	@Column(name = "register_id")
	private String registerId;	

	@Column(name = "updusr_id")
	private String updusrId;	
	
	@Column(name = "regist_dt")
	@CreationTimestamp
	private Timestamp registDt ;
	
	@Column(name="updt_dt")
	private Timestamp updtDt;	
}