package com.selldok.toy.employee.model;

import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**  * EmployeeResponse
 *
 * @author incheol.jung
 * @since 2021. 01. 21.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmployeeResponse {
	private Long id;
	private BasicInfo info;

	public static EmployeeResponse of(Employee employee) {
		return EmployeeResponse.builder().id(employee.getId()).info(employee.getInfo()).build();
	}
}
