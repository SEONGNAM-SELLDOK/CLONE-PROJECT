package com.selldok.toy.employee.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.FaceBookFriend;
import com.selldok.toy.employee.service.AuthService;
import com.selldok.toy.employee.service.EmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**  * EmployeeControllerTest
 *
 * @author incheol.jung
 * @since 2021. 01. 20.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class EmployeeControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	EmployeeService employeeService;

	@MockBean
	AuthService authService;

	@Test
	void getFriendsTest() throws Exception {
		//given
		List<FaceBookFriend> response = new ArrayList<>();
		response.add(FaceBookFriend.builder().id("1").name("incheol").build());

		given(authService.findFriends()).willReturn(response);

		//when
		ResultActions resultActions = mockMvc.perform(get("/employees/friends"));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("employees-friends",
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseFields(
					fieldWithPath("[]").description("friends"),
					fieldWithPath("[].id").description("id of friend"),
					fieldWithPath("[].name").description("name of friend")
				)
			));
	}

	@Test
	void getTest() throws Exception {
		//given
		Employee employee = Employee.builder().info(
			BasicInfo.builder().name("incheol").email("incheol@naver.com").phoneNumber("01012341234").build()).build();
		given(employeeService.get(any())).willReturn(employee);

		//when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/employees/{id}", 1L));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("employees-get",
				pathParameters(
					parameterWithName("id").description("employee id")
				),
				responseFields(
					fieldWithPath("id").description("사용자 id"),
					fieldWithPath("info.name").description("사용자 이름"),
					fieldWithPath("info.email").description("사용자 이메일"),
					fieldWithPath("info.phoneNumber").description("사용자 전화번호")
				)
			)).andReturn();
	}

	@Test
	void insert() {
		//given

		//when

		//then
	}

	@Test
	void update() {
		//given

		//when

		//then
	}

	@Test
	void delete() {
		//given

		//when

		//then
	}

	@Test
	void getProfile() {
		//given

		//when

		//then
	}

	@Test
	void updateProfile() {
		//given

		//when

		//then
	}
}
