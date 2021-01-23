package com.selldok.toy.employee.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.service.BoardService;
import com.selldok.toy.company.service.CompanyService;
import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.ApplyHistoryDto;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 김동석
 * 2021.01.22
 */
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class ApplyHistoryControllerTests {
	// gradle test 시 @Slf4j 찾을 수 없는 문제 발생하여 package lombok.extern.slf4j does not exist
	// Logger 객체를 직접 가져오도록 함
	static Logger logger = LoggerFactory.getLogger(ApplyHistoryControllerTests.class);

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CompanyService companyService;

	@Autowired
	BoardService boardService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper objectMapper;

	/**
	* 실제 업무 테스트를 위해 기업 생성, 구인 생성함
	*/
	@Test
	public void apply() throws Exception {
		Long companyId = null;
		Long boardId = null;
		Long employeeId = null;

		Employee representative = new Employee();
		employeeRepository.save(representative);
		employeeId = representative.getId();

		//이 테스트는 할 필요 없지만 다른 테스트에서 company_id 가 필요하므로 수행 함
		Address newAddress = new Address("country", "city", "street");
		Company newCompany = Company.builder()
		.address(newAddress)
		.since("since")
		.businessNum("since")
		.phone("phone")
		.terms(true)
		.representative(representative)
		.build()
		;

		companyId = companyService.create(newCompany);
		
		logger.debug("company_id={}", companyId);

		Board newBoard = Board.builder()
		.content("content")
		.image("image")
		.title("title")
		.company(newCompany)
		.build();

		boardId = boardService.create(newBoard);
		logger.debug("boardId={}", boardId);
		//assert를 굳이 할 필요는 없지만 sonarlint(java:S2699) 경고를 보이지 않게 하기 위해 넣음
		assertNotNull(boardId);

		Map<String, Object> applyData = new HashMap<>();
		applyData.put("name", "지원자명");
		applyData.put("email", "이메일주소");
		applyData.put("phoneNumber", "지원자 전화번호");
		applyData.put("employmentBoardId", boardId);
		
		// 입사지원
		MvcResult applyResult = mockMvc.perform(post("/employees/"+ employeeId + "/applyHistories")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("apply-create",
			requestFields(
				fieldWithPath("name").description("지원자명"),
				fieldWithPath("email").description("지원자 이메일"),
				fieldWithPath("phoneNumber").description("지원자 전화번호"),
				fieldWithPath("employmentBoardId").description("구인 게시물 식별자")
			),
			responseFields(
				fieldWithPath("id").description("지원이력 식별자"),
				fieldWithPath("name").description("지원자명"),
				fieldWithPath("email").description("지원자 이메일"),
				fieldWithPath("phoneNumber").description("지원자 전화번호"),
				fieldWithPath("applicantId").description("지원자 식별자"),
				fieldWithPath("representativeId").description("회사 대표자 아이디"),
				fieldWithPath("companyId").description("회사 아이디"),				
				fieldWithPath("employmentBoardId").description("구인 게시물 식별자"),
				fieldWithPath("boardTitle").description("구인 게시물 제목"),
				fieldWithPath("companyName").description("회사명"),
				fieldWithPath("companyLogoUrl").description("회사로고 주소"),
				fieldWithPath("companyCountry").description("회사 국가명"),
				fieldWithPath("companyCity").description("회사 소재지 도시명"),
				fieldWithPath("companyStreet").description("회사 소재지 도로명"),
				fieldWithPath("companyAddress").description("회사 주소"),
				fieldWithPath("appliedDate").description("지원일"),
				fieldWithPath("status").description("지원상태"),
				fieldWithPath("recommendStatus").description("추천상태")
			)
		)).andReturn();
		
		ApplyHistoryDto resultApplyHistoryDto = objectMapper.readValue(applyResult.getResponse().getContentAsString(), ApplyHistoryDto.class);
		logger.debug("applyId={}", resultApplyHistoryDto.getId());

		applyData = new HashMap<>();
		applyData.put("name", "수정할 지원자명");
		applyData.put("email", "수정할 이메일주소");
		applyData.put("phoneNumber", "수정할 지원자 전화번호");
		applyData.put("employmentBoardId", boardId);

		// 지원내용 수정
		applyResult = mockMvc.perform(put("/employees/"+ employeeId + "/applyHistories/" + resultApplyHistoryDto.getId())
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isAccepted())
		.andDo(document("apply-update",
			requestFields(
				fieldWithPath("name").description("수정할 지원자명"),
				fieldWithPath("email").description("수정할 지원자 이메일"),
				fieldWithPath("phoneNumber").description("수정할 지원자 전화번호"),
				fieldWithPath("employmentBoardId").description("구인 게시물 식별자")
			)
		)).andReturn();		

		// 상태 변경
		applyData = new HashMap<>();
		applyData.put("status", "PAPERS_PASAGE");
		applyResult = mockMvc.perform(put("/applyHistories/"+ resultApplyHistoryDto.getId() + "/changeStatus")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isAccepted())
		.andDo(document("apply-change-status",
			requestFields(
				fieldWithPath("status").description("변경할 상태")
			)
		)).andReturn();				

		// 지원 상태 카운트(지원자별
		applyResult = mockMvc.perform(get("/employees/" + employeeId + "/applyHistories/getApplyCount"))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-groupByCountByStatusOfApplicant",
			responseFields(
				fieldWithPath("APPLCN_COMPT").description("지원완료"),
				fieldWithPath("PAPERS_PASAGE").description("서류통과"),
				fieldWithPath("LAST_PSEXAM").description("최종합격"),
				fieldWithPath("DSQLFC").description("불합격"),
				fieldWithPath("CANCELED").description("신청취소"),
				fieldWithPath("allCount").description("전체 카운트")
			)
		)).andReturn();				

		// 지원 상태 카운트(회사별)
		applyResult = mockMvc.perform(get("/company/" + companyId + "/applyHistories/getApplyCount"))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-groupByCountByStatusOfCompany",
			responseFields(
				fieldWithPath("APPLCN_COMPT").description("지원완료"),
				fieldWithPath("PAPERS_PASAGE").description("서류통과"),
				fieldWithPath("LAST_PSEXAM").description("최종합격"),
				fieldWithPath("DSQLFC").description("불합격"),
				fieldWithPath("CANCELED").description("신청취소"),
				fieldWithPath("allCount").description("전체 카운트")
			)
		)).andReturn();		

		// 개인별 지원이력 검색
		applyResult = mockMvc.perform(
			get("/employees/" + employeeId + "/applyHistories")
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-employeesApplyHistoriesSearch"
			,requestParameters(
				parameterWithName("name").description("검색할 지원자명")
				,parameterWithName("companyName").description("검색할 회사명")
				,parameterWithName("status").description("검색할 상태")
			)	
			,responseFields(
				fieldWithPath("[].id").description("지원이력 식별자"),
				fieldWithPath("[].name").description("지원자명"),
				fieldWithPath("[].email").description("지원자 이메일"),
				fieldWithPath("[].phoneNumber").description("지원자 전화번호"),
				fieldWithPath("[].applicantId").description("지원자 식별자"),
				fieldWithPath("[].representativeId").description("회사 대표자 아이디"),
				fieldWithPath("[].companyId").description("회사 아이디"),				
				fieldWithPath("[].employmentBoardId").description("구인 게시물 식별자"),
				fieldWithPath("[].boardTitle").description("구인 게시물 제목"),
				fieldWithPath("[].companyName").description("회사명"),
				fieldWithPath("[].companyLogoUrl").description("회사로고 주소"),
				fieldWithPath("[].companyCountry").description("회사 국가명"),
				fieldWithPath("[].companyCity").description("회사 소재지 도시명"),
				fieldWithPath("[].companyStreet").description("회사 소재지 도로명"),
				fieldWithPath("[].companyAddress").description("회사 주소"),
				fieldWithPath("[].appliedDate").description("지원일"),
				fieldWithPath("[].status").description("지원상태"),
				fieldWithPath("[].recommendStatus").description("추천상태")
			)
		)).andReturn();				

		// 회사별 지원이력 검색
		applyResult = mockMvc.perform(
			get("/company/" + companyId + "/applyHistories")
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-companyApplyHistoriesSearch"
			,requestParameters(
				parameterWithName("name").description("검색할 지원자명")
				,parameterWithName("companyName").description("검색할 회사명")
				,parameterWithName("status").description("검색할 상태")
			)	
			,responseFields(
				fieldWithPath("[].id").description("지원이력 식별자"),
				fieldWithPath("[].name").description("지원자명"),
				fieldWithPath("[].email").description("지원자 이메일"),
				fieldWithPath("[].phoneNumber").description("지원자 전화번호"),
				fieldWithPath("[].applicantId").description("지원자 식별자"),
				fieldWithPath("[].representativeId").description("회사 대표자 아이디"),
				fieldWithPath("[].companyId").description("회사 아이디"),				
				fieldWithPath("[].employmentBoardId").description("구인 게시물 식별자"),
				fieldWithPath("[].boardTitle").description("구인 게시물 제목"),
				fieldWithPath("[].companyName").description("회사명"),
				fieldWithPath("[].companyLogoUrl").description("회사로고 주소"),
				fieldWithPath("[].companyCountry").description("회사 국가명"),
				fieldWithPath("[].companyCity").description("회사 소재지 도시명"),
				fieldWithPath("[].companyStreet").description("회사 소재지 도로명"),
				fieldWithPath("[].companyAddress").description("회사 주소"),
				fieldWithPath("[].appliedDate").description("지원일"),
				fieldWithPath("[].status").description("지원상태"),
				fieldWithPath("[].recommendStatus").description("추천상태")
			)
		)).andReturn();	

		// 대표자 id로 회사별 지원이력 검색
		applyResult = mockMvc.perform(
			get("/employees/" + employeeId + "/company/applyHistories")
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-representativeCompanyApplyHistoriesSearch"
			,requestParameters(
				parameterWithName("name").description("검색할 지원자명")
				,parameterWithName("companyName").description("검색할 회사명")
				,parameterWithName("status").description("검색할 상태")
			)	
			,responseFields(
				fieldWithPath("[].id").description("지원이력 식별자"),
				fieldWithPath("[].name").description("지원자명"),
				fieldWithPath("[].email").description("지원자 이메일"),
				fieldWithPath("[].phoneNumber").description("지원자 전화번호"),
				fieldWithPath("[].applicantId").description("지원자 식별자"),
				fieldWithPath("[].representativeId").description("회사 대표자 아이디"),
				fieldWithPath("[].companyId").description("회사 아이디"),				
				fieldWithPath("[].employmentBoardId").description("구인 게시물 식별자"),
				fieldWithPath("[].boardTitle").description("구인 게시물 제목"),
				fieldWithPath("[].companyName").description("회사명"),
				fieldWithPath("[].companyLogoUrl").description("회사로고 주소"),
				fieldWithPath("[].companyCountry").description("회사 국가명"),
				fieldWithPath("[].companyCity").description("회사 소재지 도시명"),
				fieldWithPath("[].companyStreet").description("회사 소재지 도로명"),
				fieldWithPath("[].companyAddress").description("회사 주소"),
				fieldWithPath("[].appliedDate").description("지원일"),
				fieldWithPath("[].status").description("지원상태"),
				fieldWithPath("[].recommendStatus").description("추천상태")
			)
		)).andReturn();	
	}	
}
