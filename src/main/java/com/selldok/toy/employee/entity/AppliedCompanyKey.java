package com.selldok.toy.employee.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DongSeok, Kim
 */

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedCompanyKey implements Serializable {
	private static final long serialVersionUID = -4816010187279557320L;

	@Column(name = "applicant_id")
	private Long applicantId;

	@Column(name = "company_id")
	private Long companyId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AppliedCompanyKey comparingAppliedCompanyKey = (AppliedCompanyKey)obj;
		return comparingAppliedCompanyKey.getApplicantId().equals(applicantId)
				&& comparingAppliedCompanyKey.getCompanyId().equals(companyId);
	}

	@Override
	public int hashCode() {
			return Objects.hash(applicantId, companyId);
	}	
}
