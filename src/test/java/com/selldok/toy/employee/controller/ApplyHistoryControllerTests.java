package com.selldok.toy.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.employee.entity.ApplyHistory.Status;
import com.selldok.toy.employee.model.ApplyHistoryDto;
import com.selldok.toy.employee.service.AppliedHistoryService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
	ObjectMapper objectMapper;

	@MockBean
	private AppliedHistoryService applyHistoryService;	

	private ApplyHistoryDto newApplyHistoryDto = ApplyHistoryDto.builder()
		.id(1L)
		.name("지원자명")
		.email("이메일주소")
		.phoneNumber("전화번호")
		.applicantId(1L)
		.representativeId(1L)
		.companyId(1L)
		.employmentBoardId(1L)
		.boardTitle("구인 제목")
		.companyName("회사명")
		.companyLogoUrl("회사 로고 주소")
		.companyCountry("회사 소재 국가")
		.companyCity("회사 소재 도시")
		.companyStreet("회사 주소 도로명")
		.appliedDate(new Timestamp(new Date().getTime()))
		.status(Status.APPLCN_COMPT)
		.recommendStatus("추천 상태")
		.build()
	;	

	/**
	* 입사 지원
	 * @throws Exception
	 */
	@Test
	public void apply() throws Exception {
		given(applyHistoryService.create(any())).willReturn(newApplyHistoryDto);

		Map<String, Object> applyData = new HashMap<>();
		applyData.put("name", "지원자명");
		applyData.put("email", "이메일주소");
		applyData.put("phoneNumber", "지원자 전화번호");
		applyData.put("employmentBoardId", 1L);

		// 입사지원
		mockMvc.perform(post("/employees/{employeeId}/applyHistories", 1L)
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("apply-create",
			pathParameters( 
				parameterWithName("employeeId").description("구직자 식별자")
			),
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
		));
	}

	/**
	 * 지원 내용 수정
	 * @throws Exception
	 */
	@Test
	public void update() throws Exception {
		Map<String, Object> applyData = new HashMap<>();
		applyData.put("name", "수정할 지원자명");
		applyData.put("email", "수정할 이메일주소");
		applyData.put("phoneNumber", "수정할 지원자 전화번호");
		applyData.put("employmentBoardId", 1L);

		given(applyHistoryService.update(any())).willReturn(true);		

		// 지원내용 수정
		mockMvc.perform(put("/employees/{employeeId}/applyHistories/{applyHistoryId}", 1L, 1L)
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isAccepted())
		.andDo(document("apply-update",
			pathParameters( 
				parameterWithName("employeeId").description("구직자 식별자")
				,parameterWithName("applyHistoryId").description("지원 이력 식별자")
			),		
			requestFields(
				fieldWithPath("name").description("수정할 지원자명."),
				fieldWithPath("email").description("수정할 지원자 이메일"),
				fieldWithPath("phoneNumber").description("수정할 지원자 전화번호"),
				fieldWithPath("employmentBoardId").description("구인 게시물 식별자")
			)
		));	
	}

	/**
	 * 지원 상태 변경
	 * @throws Exception
	 */
	@Test
	public void updateStatus() throws Exception {
		given(applyHistoryService.update(any())).willReturn(true);		

		Map<String, Object> applyData = new HashMap<>();
		applyData.put("status", "PAPERS_PASAGE");

		mockMvc.perform(put("/applyHistories/{applyHistoryId}/changeStatus", 1L)
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(applyData)))
		.andDo(print())
		.andExpect(status().isAccepted())
		.andDo(document("apply-change-status",
			pathParameters( 
				parameterWithName("applyHistoryId").description("지원 이력 식별자")
			),			
			requestFields(
				fieldWithPath("status").description("변경할 상태")
			)
		));
	}

	/**
	 * 지원 상태 카운트(지원자별)
	 * @throws Exception
	 */
	@Test
	public void groupByCountByStatusOfApplicant() throws Exception {	
		Map<String, Long> groupByCount = new HashMap<>();
		groupByCount.put("APPLCN_COMPT", 1L);
		groupByCount.put("PAPERS_PASAGE", 2L);
		groupByCount.put("LAST_PSEXAM", 3L);
		groupByCount.put("DSQLFC", 4L);
		groupByCount.put("CANCELED", 5L);
		groupByCount.put("allCount", 6L);

		given(applyHistoryService.groupByCountByStatusOfApplicant(any())).willReturn(groupByCount);	

		mockMvc.perform(get("/employees/{employeeId}/applyHistories/getApplyCount", 1L))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-groupByCountByStatusOfApplicant",
			pathParameters( 
				parameterWithName("employeeId").description("구직자 식별자")
			),				
			responseFields(
				fieldWithPath("APPLCN_COMPT").description("지원완료"),
				fieldWithPath("PAPERS_PASAGE").description("서류통과"),
				fieldWithPath("LAST_PSEXAM").description("최종합격"),
				fieldWithPath("DSQLFC").description("불합격"),
				fieldWithPath("CANCELED").description("신청취소"),
				fieldWithPath("allCount").description("전체 카운트")
			)
		));	
	}	

	/**
	 * 지원 상태 카운트(회사별)
	 * @throws Exception
	 */
	@Test
	public void groupByCountByStatusOfCompany() throws Exception {	
		Map<String, Long> groupByCount = new HashMap<>();
		groupByCount.put("APPLCN_COMPT", 1L);
		groupByCount.put("PAPERS_PASAGE", 2L);
		groupByCount.put("LAST_PSEXAM", 3L);
		groupByCount.put("DSQLFC", 4L);
		groupByCount.put("CANCELED", 5L);
		groupByCount.put("allCount", 6L);

		given(applyHistoryService.groupByCountByStatusOfCompany(any())).willReturn(groupByCount);	

		mockMvc.perform(get("/company/{companyId}/applyHistories/getApplyCount", 1L))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-groupByCountByStatusOfCompany",

			pathParameters( 
				parameterWithName("companyId").description("회사 식별자")
			),				
			responseFields(
				fieldWithPath("APPLCN_COMPT").description("지원완료"),
				fieldWithPath("PAPERS_PASAGE").description("서류통과"),
				fieldWithPath("LAST_PSEXAM").description("최종합격"),
				fieldWithPath("DSQLFC").description("불합격"),
				fieldWithPath("CANCELED").description("신청취소"),
				fieldWithPath("allCount").description("전체 카운트")
			)
		));		
	}		

	/**
	 * 개인별 지원이력 검색
	 * @throws Exception
	 */
	@Test
	public void employeesApplyHistoriesSearch() throws Exception {	
		List<ApplyHistoryDto> applyHistoryDtoList = new ArrayList<>();
		applyHistoryDtoList.add(newApplyHistoryDto);
		
		given(applyHistoryService.search(any(), any())).willReturn(applyHistoryDtoList);		

		mockMvc.perform(
			get("/employees/{employeeId}/applyHistories", 1L)
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-employeesApplyHistoriesSearch",
			pathParameters( 
				parameterWithName("employeeId").description("지원자 식별자")
			),			
			requestParameters(
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
		));			
	}		

	/**
	 * 회사별 지원이력 검색
	 * @throws Exception
	 */
	@Test
	public void companyApplyHistoriesSearch() throws Exception {	
		List<ApplyHistoryDto> applyHistoryDtoList = new ArrayList<>();
		applyHistoryDtoList.add(newApplyHistoryDto);
		
		given(applyHistoryService.search(any(), any())).willReturn(applyHistoryDtoList);		

		mockMvc.perform(
			get("/company/{companyId}/applyHistories", 1L)
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-companyApplyHistoriesSearch",
			pathParameters( 
				parameterWithName("companyId").description("회사 식별자")
			),						
			requestParameters(
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
		));			
	}

	/**
	 * 대표자 id로 회사별 지원이력 검색
	 * @throws Exception
	 */
	@Test
	public void representativeCompanyApplyHistoriesSearch() throws Exception {	
		List<ApplyHistoryDto> applyHistoryDtoList = new ArrayList<>();
		applyHistoryDtoList.add(newApplyHistoryDto);
		
		given(applyHistoryService.search(any(), any())).willReturn(applyHistoryDtoList);		

		mockMvc.perform(
			get("/employees/{employeeId}/company/applyHistories", 1L)
			.param("name", "지원자명")
			.param("companyName", "회사명")
			.param("status", "PAPERS_PASAGE")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document(
			"apply-representativeCompanyApplyHistoriesSearch"
			,pathParameters( 
				parameterWithName("employeeId").description("대표자 식별자")
			)
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
		));			
	}
}
