package com.selldok.toy.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.salary.model.SalaryRequest;
import com.selldok.toy.salary.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Seil Park
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class SalaryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SalaryService salaryService;


    @Test
    void getSalaryTest() throws Exception {
        // salary는 미리 들어가있음
        mockMvc.perform(RestDocumentationRequestBuilders.get("/salary/{id}",1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-salary",
                        pathParameters(
                                parameterWithName("id").description("id of salary")
                        ),
                        responseFields(
                                fieldWithPath("occupation").description("occupation of salary")
                                ,fieldWithPath("yearOne").description("yearOne of salary")
                                ,fieldWithPath("yearTwo").description("yearTwo of salary")
                                ,fieldWithPath("yearThree").description("yearThree of salary")
                                ,fieldWithPath("yearFour").description("yearFour of salary")
                                ,fieldWithPath("yearFive").description("yearFive of salary")
                                ,fieldWithPath("yearSix").description("yearSix of salary")
                                ,fieldWithPath("yearSeven").description("yearSeven of salary")
                                ,fieldWithPath("yearEight").description("yearEight of salary")
                                ,fieldWithPath("yearNine").description("yearNine of salary")
                                ,fieldWithPath("yearTen").description("yearTen of salary")
                        )
                ));
    }

    @Test
    void updateSalaryTest() throws Exception {
        SalaryRequest salaryRequest = SalaryRequest.builder()
                .yearOne(1000).yearTwo(2000).yearThree(3000).yearFour(4000).yearFive(5000)
                .yearSix(6000).yearSeven(7000).yearEight(8000).yearNine(9000).yearTen(10000).build();

        mockMvc.perform(put("/salary/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(salaryRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("edit-salary",
                        pathParameters(
                                parameterWithName("id").description("id of salary")
                        ),
                        requestFields(
                                fieldWithPath("yearOne").description("yearOne of salary")
                                ,fieldWithPath("yearTwo").description("yearTwo of salary")
                                ,fieldWithPath("yearThree").description("yearThree of salary")
                                ,fieldWithPath("yearFour").description("yearFour of salary")
                                ,fieldWithPath("yearFive").description("yearFive of salary")
                                ,fieldWithPath("yearSix").description("yearSix of salary")
                                ,fieldWithPath("yearSeven").description("yearSeven of salary")
                                ,fieldWithPath("yearEight").description("yearEight of salary")
                                ,fieldWithPath("yearNine").description("yearNine of salary")
                                ,fieldWithPath("yearTen").description("yearTen of salary")
                        )
                ));
    }
}
