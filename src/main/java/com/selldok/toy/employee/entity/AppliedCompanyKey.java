package com.selldok.toy.employee.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DongSeok, Kim
 */

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedCompanyKey implements Serializable {
	private static final long serialVersionUID = -4816010187279557320L;

	private Long applicantId;
	private Long companyId;	
}
