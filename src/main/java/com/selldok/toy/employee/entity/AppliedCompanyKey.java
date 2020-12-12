package com.selldok.toy.employee.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author DongSeok, Kim
 */

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedCompanyKey implements Serializable {
	private static final long serialVersionUID = -4816010187279557320L;

	@Column(name = "applicant_id")
  Long applicantId;

  @Column(name = "company_id")
	Long companyId;

	public boolean equals(AppliedCompanyKey comparingAppliedCompanyKey) {
		boolean isEqual = false;
		if(comparingAppliedCompanyKey.getApplicantId().equals(applicantId) 
			&& comparingAppliedCompanyKey.getCompanyId().equals(companyId)) {
			isEqual = false;
		}
		return isEqual;
	}
}
