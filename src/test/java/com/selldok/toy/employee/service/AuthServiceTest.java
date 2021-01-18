package com.selldok.toy.employee.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.FaceBookTokenResponse;
import com.selldok.toy.employee.model.InsertEmployeeRequest;

/**  * AuthServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 19.
 */
@SpringBootTest
@Transactional
class AuthServiceTest {
	private AuthService authService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		authService = new AuthService(employeeRepository, restTemplate);
		InsertEmployeeRequest request = InsertEmployeeRequest.builder().email("incheol@naver.com").build();
		employeeService.insert(request);
	}

	@Test
	void findUserInfoByEmail() {
		Optional<Employee> optionalEmployee = authService.findUserInfoByEmail("incheol@naver.com");
		Assertions.assertTrue(optionalEmployee.isPresent());
	}

	@Test
	void validateToken() {
		FaceBookTokenResponse response = FaceBookTokenResponse.builder().email("incheol@naver.com").build();
		when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class),
			Mockito.any(Object[].class))).thenReturn(response);
		FaceBookTokenResponse response1 = authService.validateToken("test");
		Assertions.assertEquals(response1.getEmail(), response.getEmail());
	}

	@Test
	void findFriends() {
	}
}
