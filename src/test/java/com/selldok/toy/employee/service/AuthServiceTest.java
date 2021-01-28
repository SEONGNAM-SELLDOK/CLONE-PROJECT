package com.selldok.toy.employee.service;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import com.selldok.toy.config.ROLE;
import com.selldok.toy.config.SelldokUserToken;
import com.selldok.toy.repository.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.FaceBookFriendResult;
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
		FaceBookTokenResponse mockResponse = FaceBookTokenResponse.builder().email("incheol@naver.com").build();
		when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class),
			Mockito.any(Object[].class))).thenReturn(mockResponse);
		FaceBookTokenResponse faceBookTokenResponse = authService.validateToken("test");
		Assertions.assertEquals(faceBookTokenResponse.getEmail(), mockResponse.getEmail());
	}

	@Test
	void findFriends() {
		Authentication authentication = new SelldokUserToken(1L, "accessToken", "name", "email", "phoneNumber",
			"picUrl",
			ROLE.BASIC, "authId");
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		FaceBookFriendResult mockResponse = FaceBookFriendResult.builder().data(Collections.emptyList()).build();
		when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class),
			Mockito.any(Object[].class))).thenReturn(mockResponse);

		authService.findFriends();
		verify(restTemplate, times(1)).getForObject(Mockito.anyString(), Mockito.any(Class.class),
			Mockito.any(Object[].class));

	}
}
