package com.selldok.toy.employee.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.employee.entity.BasicInfo;
import com.selldok.toy.employee.entity.Company;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.entity.Expertise;
import com.selldok.toy.employee.entity.PersonInfo;
import com.selldok.toy.employee.entity.School;
import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.FaceBookFriend;
import com.selldok.toy.employee.model.InsertEmployeeRequest;
import com.selldok.toy.employee.model.UpdateEmployeeRequest;
import com.selldok.toy.employee.model.UpdateProfileRequest;
import com.selldok.toy.employee.service.AuthService;
import com.selldok.toy.employee.service.EmployeeService;

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
			.andDo(document("employees-getFriends",
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
	void insert() throws Exception {
		//given
		doNothing().when(employeeService).insert(any());
		InsertEmployeeRequest request = InsertEmployeeRequest.builder().name("incheol").build();

		//when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isAccepted())
			.andDo(document("employees-insert",
				requestFields(
					fieldWithPath("name").description("Name of employee"),
					fieldWithPath("email").description("email of employee"),
					fieldWithPath("phoneNumber").description("phoneNumber of employee")
				)
			)).andReturn();
	}

	@Test
	void update() throws Exception {
		//given
		doNothing().when(employeeService).update(any(), any());
		UpdateEmployeeRequest request = UpdateEmployeeRequest.builder().name("incheol").build();

		//when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.put("/employees/{id}", "1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isAccepted())
			.andDo(document("employees-update",
				pathParameters(
					parameterWithName("id").description("employee id")
				),
				requestFields(
					fieldWithPath("name").description("Name of employee"),
					fieldWithPath("email").description("email of employee"),
					fieldWithPath("phoneNumber").description("phoneNumber of employee")
				)
			)).andReturn();
	}

	@Test
	void delete() throws Exception {
		//given
		doNothing().when(employeeService).delete(any());

		//when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/employees/{id}", "1"));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(document("employees-delete",
				pathParameters(
					parameterWithName("id").description("employee id")
				)
			)).andReturn();
	}

	@Test
	void getProfile() throws Exception {
		//given
		EmployeeProfileResponse response = EmployeeProfileResponse.builder()
			.info(BasicInfo.builder().build())
			.personInfo(
				PersonInfo.builder()
					.school(School.builder().build())
					.company(Company.builder().build())
					.expertise(Expertise.builder().build()).build())
			.build();
		given(employeeService.getProfile(any())).willReturn(response);
		//when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.get("/employees/profile/{id}", "1"));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("employees-getProfile",
				pathParameters(
					parameterWithName("id").description("employee id")
				),
				responseFields(
					fieldWithPath("id").description("사용자 id"),
					fieldWithPath("info.name").description("사용자 이름"),
					fieldWithPath("info.email").description("사용자 이메일"),
					fieldWithPath("info.phoneNumber").description("사용자 전화번호"),
					fieldWithPath("personInfo.id").description("기본정보 id"),
					fieldWithPath("personInfo.employeeId").description("기본정보 사용자 id"),
					fieldWithPath("personInfo.resume").description("기본정보 이력서"),
					fieldWithPath("personInfo.school.schoolName").description("학교 이름"),
					fieldWithPath("personInfo.school.department").description("학교 전공"),
					fieldWithPath("personInfo.company.companyName").description("회사 이름"),
					fieldWithPath("personInfo.company.position").description("회사 부서"),
					fieldWithPath("personInfo.company.rank").description("회사 직급"),
					fieldWithPath("personInfo.expertise.occupation").description("경력 포지션"),
					fieldWithPath("personInfo.expertise.task").description("경력 업무"),
					fieldWithPath("personInfo.expertise.carrer").description("경력 기간"),
					fieldWithPath("personInfo.expertise.skills").description("경력 기술")
				)
			)).andReturn();
	}

	@Test
	void updateProfile() throws Exception {
		//given
		doNothing().when(employeeService).updateProfile(any(), any());
		UpdateProfileRequest request = UpdateProfileRequest.builder()
			.resume("")
			.school(School.builder().build())
			.company(Company.builder().build())
			.expertise(Expertise.builder().build())
			.build();

		//when
		ResultActions resultActions = mockMvc.perform(
			RestDocumentationRequestBuilders.put("/employees/profile/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isAccepted())
			.andDo(document("employees-updateProfile",
				pathParameters(
					parameterWithName("id").description("employee id")
				),
				requestFields(
					fieldWithPath("resume").description("기본정보 이력서"),
					fieldWithPath("school.schoolName").description("학교 이름"),
					fieldWithPath("school.department").description("학교 전공"),
					fieldWithPath("company.companyName").description("회사 이름"),
					fieldWithPath("company.position").description("회사 부서"),
					fieldWithPath("company.rank").description("회사 직급"),
					fieldWithPath("expertise.occupation").description("경력 포지션"),
					fieldWithPath("expertise.task").description("경력 업무"),
					fieldWithPath("expertise.carrer").description("경력 기간"),
					fieldWithPath("expertise.skills").description("경력 기술")
				)
			)).andReturn();
	}
}
