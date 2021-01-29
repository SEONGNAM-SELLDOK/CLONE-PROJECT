package com.selldok.toy.employee.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.selldok.toy.employee.dao.PersonInfoRepository;
import com.selldok.toy.employee.entity.Company;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.entity.Expertise;
import com.selldok.toy.employee.entity.PersonInfo;
import com.selldok.toy.employee.entity.School;
import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;
import com.selldok.toy.employee.model.UpdateProfileRequest;

/**  * EmployeeServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 19.
 */
@SpringBootTest
@Transactional
public class EmployeeServiceTest {
	@Autowired
	EmployeeService employeeService;

	@Autowired
	PersonInfoRepository personInfoRepository;

	@BeforeEach
	void setup() {
		InsertEmployeeRequest request = InsertEmployeeRequest.builder().email("incheol@naver.com").build();
		employeeService.insert(request);
	}

	@Test
	void get() {
		Employee employee = employeeService.findByEmail("incheol@naver.com");
		Employee existingEmployee = employeeService.get(employee.getId());
		Assertions.assertEquals(existingEmployee.getId(), employee.getId());
	}

	@Test
	void insert() {
		InsertEmployeeRequest request = InsertEmployeeRequest.builder().email("test@naver.com").build();
		employeeService.insert(request);
		Employee employee = employeeService.findByEmail("test@naver.com");
		Assertions.assertEquals(request.getEmail(), employee.getInfo().getEmail());
	}

	@Test
	void update() {
		UpdateEmployeeRequest request = UpdateEmployeeRequest.builder().email("steve@naver.com").build();
		Employee employee = employeeService.findByEmail("incheol@naver.com");
		employeeService.update(employee.getId(), request);

		Employee employee1 = employeeService.get(employee.getId());
		Assertions.assertEquals(employee1.getInfo().getEmail(), "steve@naver.com");
	}

	@Test
	void delete() {
		Employee employee = employeeService.findByEmail("incheol@naver.com");
		employeeService.delete(employee.getId());

		Assertions.assertThrows(RuntimeException.class, () -> {
			employeeService.get(employee.getId());
		});
	}

	@Test
	void getProfile() {
		Employee employee = employeeService.findByEmail("incheol@naver.com");
		EmployeeProfileResponse employeeProfileResponse = employeeService.getProfile(employee.getId());
		Assertions.assertEquals(employeeProfileResponse.getInfo().getEmail(), "incheol@naver.com");
	}

	@Test
	void updateProfile() {
		UpdateProfileRequest request = UpdateProfileRequest.builder()
			.company(Company.builder().companyName("naver").build())
			.expertise(Expertise.builder().build())
			.school(School.builder().build())
			.build();
		Employee employee = employeeService.findByEmail("incheol@naver.com");
		employeeService.updateProfile(employee.getId(), request);

		PersonInfo personInfo = personInfoRepository.findByEmployeeId(employee.getId()).get();
		EmployeeProfileResponse response = employeeService.getProfile(employee.getId());
		Assertions.assertEquals(response.getPersonInfo().getCompany().getCompanyName(), personInfo.getCompany().getCompanyName());
	}

}
