package com.selldok.toy.company.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.company.controller.CompanyController;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.dao.CompanySearchCondition;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.CompanyCreateRequest;
import com.selldok.toy.company.model.CompanyListResponse;
import com.selldok.toy.company.model.CompanyProfileResponse;
import com.selldok.toy.company.model.CompanyUpdateRequest;
import com.selldok.toy.company.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class CompanyControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CompanyService companyService;

    private long id;

    @BeforeEach
    public void setup() {
        Address address = new Address("삼성전자1111", "KR", "seoul");
        Company company = Company.builder()
                .name("삼성전자1111")
                .address(address)
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

        id = companyService.create(company);
    }

    @Test // 정상적으로 기업 생성
    public void createCompany() throws Exception {
        CompanyCreateRequest companyCreateRequest = new CompanyCreateRequest().builder()
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


        mockMvc.perform(post("/company")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(companyCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType))
                .andExpect(jsonPath("company_id").exists())
                .andDo(document("create-company",
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    requestFields(
                        fieldWithPath("name").description("Name of new company"),
                        fieldWithPath("country").description("country of new company"),
                        fieldWithPath("city").description("city of new company"),
                        fieldWithPath("street").description("street of new company"),
                        fieldWithPath("businessNum").description("businessNum of new company"),
                        fieldWithPath("totalSales").description("totalSales of new company"),
                        fieldWithPath("employees").description("employees of new company"),
                        fieldWithPath("info").description("info of new company"),
                        fieldWithPath("email").description("email of new company"),
                        fieldWithPath("since").description("since of new company"),
                        fieldWithPath("phone").description("phone of new company"),
                        fieldWithPath("homepage").description("homepage of new company"),
                        fieldWithPath("terms").description("terms of new company")
                    ),
                    responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                    ),
                    responseFields(
                        fieldWithPath("company_id").description("company_id of new company")
                    )
                ));
    }

    @Test
    void companyUpdateTest() throws Exception {
        CompanyUpdateRequest companyUpdateRequest = CompanyUpdateRequest.builder()
                .name("삼성전자1111")
                .country("KR")
                .city("seoul")
                .street("성남대로 1111")
                .totalSales("190000")
                .employees("10to100")
                .info("소개 수정 합니다.")
                .email("ggs0707@naver.com")
                .phone("01045451212")
                .homepage("https://www.wanted.co.kr/")
                .build();

        companyService.update(id, companyUpdateRequest);

        Optional<Company> companyId = companyService.findById(id);
        Assertions.assertEquals(companyId.get().getInfo(), "소개 수정 합니다.");
        Assertions.assertEquals(companyId.get().getPhone(), "01045451212");

        mockMvc.perform(put("/company/1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(companyUpdateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("company_id").exists())
                .andDo(document("update-company",
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    requestFields(
                        fieldWithPath("name").description("Name of new company"),
                        fieldWithPath("country").description("country of new company"),
                        fieldWithPath("city").description("city of new company"),
                        fieldWithPath("street").description("street of new company"),
                        fieldWithPath("totalSales").description("totalSales of new company"),
                        fieldWithPath("employees").description("employees of new company"),
                        fieldWithPath("info").description("info of new company"),
                        fieldWithPath("email").description("email of new company"),
                        fieldWithPath("phone").description("phone of new company"),
                        fieldWithPath("homepage").description("homepage of new company")
                    ),
                    responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                    ),
                    responseFields(
                        fieldWithPath("company_id").description("company_id of new company")
                    )
                ));

    }

    @Test
    void companyDeleteTest() {
        companyService.delete(id);

        Optional<Company> company = companyService.findById(id);
        Assertions.assertTrue(company.isEmpty());

    }

    @Test
    void getCompanyProfile() {
        Optional<Company> company = companyService.findById(id);
        Assertions.assertFalse(company.isEmpty());
    }



}
