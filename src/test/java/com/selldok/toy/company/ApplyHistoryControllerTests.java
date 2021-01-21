package com.selldok.toy.company;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.company.model.CompanyCreateRequest;
import com.selldok.toy.company.service.CompanyService;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class ApplyHistoryControllerTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	CompanyService companyService;

	@Autowired
	ObjectMapper objectMapper;
	
	static Integer company_id = null;

	/**
	* 실제 업무 테스트를 위해 기업 생성, 구인 생성
	* 이 테스트는 할 필요 없지만 다른 테스트에서 company_id 가 필요하므로 수행 함
	*/
	@Test
	@Order(1)
	public void createCompany() throws Exception {
		CompanyCreateRequest companyCreateRequest = CompanyCreateRequest.builder()
		.name("삼성전자1111")
		.country("KR")
		.city("seoul")
		.street("성남대로 1111")
		.businessNum("1212131415")
		.totalSales("190000")
		.employees("10to100")
		.info("삼성전자 소개입니다.")
		.email("ggs0707@naver.com")
		.since("2020")
		.phone("01012127878")
		.homepage("https://www.wanted.co.kr/")
		.terms(true)
		.build();

		MvcResult result = mockMvc.perform(post("/company")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(objectMapper.writeValueAsString(companyCreateRequest)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("company_id").exists())
		.andDo(document("create-company",
			requestHeaders(
				headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
			),
			responseHeaders(
				headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			responseFields(
				fieldWithPath("company_id").description("company_id of new company")
			)
		)).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode company = mapper.readTree(result.getResponse().getContentAsString());
		company_id = company.path("company_id").asInt();
		log.debug("company_id={}", company_id);
		assertNotNull(company_id);
	}

	@Test
	@Order(2)
	public void apply() throws Exception {
		log.debug("company_id={}", company_id);
		assertTrue(company_id != null);
	}

}
