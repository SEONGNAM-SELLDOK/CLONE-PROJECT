package com.selldok.toy.employee.entity;

import java.sql.Timestamp;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PreUpdate;

import com.selldok.toy.company.entity.Company;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지원한 회사 (하지만 지원자는 회사에 지원하는 것이 아니고 '공고'에 지원한다. 수정 필요)
 * 
 * @author DongSeok, Kim
 */

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedCompany {
	@EmbeddedId
	private AppliedCompanyKey appliedCompanyKey;

	@Embedded
	private BasicInfo info;

	@CreationTimestamp
	// @UpdateTimestamp // Caused by: org.hibernate.AnnotationException: Only one
	// generator annotation is allowed
	private Timestamp appliedDt;

	@ManyToOne
	@MapsId("applicantId")
	@JoinColumn(name = "applicant_id")
	private Employee applicant;

	@ManyToOne
	@MapsId("companyId")
	@JoinColumn(name = "company_id")
	private Company appliedCompany;

	@PreUpdate
	protected void onUpdate() {
		appliedDt = new Timestamp(System.currentTimeMillis());
	}
}
