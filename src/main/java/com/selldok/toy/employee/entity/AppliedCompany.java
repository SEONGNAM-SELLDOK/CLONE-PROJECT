package com.selldok.toy.employee.entity;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.selldok.toy.company.entity.Company;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
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

	@CreationTimestamp
	private Timestamp appliedDt;
	@ManyToOne
	@MapsId("applicantId")
	@JoinColumn(name = "applicant_id")
	private Employee applicant;
	@ManyToOne
	@MapsId("companyId")
	@JoinColumn(name = "company_id")
	private Company appliedCompany;
}
