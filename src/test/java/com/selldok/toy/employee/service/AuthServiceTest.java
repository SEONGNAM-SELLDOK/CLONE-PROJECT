package com.selldok.toy.employee.service;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.event.entity.EventType;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.service.EventService;
import lombok.RequiredArgsConstructor;

/**  * AuthServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 19.
 */
@SpringBootTest
@Transactional
class AuthServiceTest {
	@Autowired
	private AuthService authService;
	@Autowired
	private EmployeeService employeeService;

	@BeforeEach
	public void setup() {
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
		authService.validateToken()
	}

	@Test
	void findFriends() {
	}
}
