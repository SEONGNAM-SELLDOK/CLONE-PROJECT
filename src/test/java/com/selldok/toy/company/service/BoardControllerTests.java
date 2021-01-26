package com.selldok.toy.company.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.common.RestDocsConfiguration;
import com.selldok.toy.company.controller.BoardController;
import com.selldok.toy.company.controller.CompanyController;
import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Address;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.*;
import com.selldok.toy.company.service.BoardService;
import com.selldok.toy.company.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class BoardControllerTests {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CompanyService companyService;
    @Autowired BoardService boardService;
    @Autowired BoardController boardController;

    private long id;
    private long boardId;

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

        Company company1 = companyService.findById(id).get();
        Board board = new Board().builder()
                .title("백엔드 개발자 채용합니다.111")
                .content("채용 내용111")
                .image("/img/0000.jpg")
                .endDate(new Date())
                .company(company1)
                .build();

        boardId = boardService.create(board);
    }

    @Test
    void createBoard() throws Exception {
        BoardCreateRequest board = new BoardCreateRequest().builder()
                .title("백엔드 개발자 채용합니다.")
                .content("채용 내용")
                .image("/img/0000.jpg")
                .endDate(new Date())
                .companyId(id)
                .build();

        boardController.create(board);
        Optional<Board> nBoard = boardService.findById(boardId);
        Assertions.assertTrue(nBoard.isPresent());

        mockMvc.perform(post("/board/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(board)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("create-board",
            requestHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
            ),
            requestFields(
                    fieldWithPath("title").description("title of new board"),
                    fieldWithPath("content").description("content of new board"),
                    fieldWithPath("image").description("image of new board"),
                    fieldWithPath("endDate").description("endDate of new board"),
                    fieldWithPath("companyId").description("companyId of new board")
            ),
            responseHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
            ),
            responseFields(
                    fieldWithPath("board_id").description("board_id of new board")
            )
        ));
    }

    @Test
    void updateBoard() throws Exception {
        BoardUpdateRequest request = BoardUpdateRequest.builder()
                .title("back-end title")
                .content("채용 내용 수정합니다.")
                .image("/img/0000.jpg")
                .endDate(new Date())
                .build();

        boardService.update(boardId, request);

        Optional<Board> byId = boardService.findById(boardId);
        Assertions.assertEquals(byId.get().getTitle(), "back-end title");

        mockMvc.perform(put("/board/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-board",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("title").description("title of update board"),
                                fieldWithPath("content").description("content of update board"),
                                fieldWithPath("image").description("image of update board"),
                                fieldWithPath("endDate").description("endDate of update board")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("board_id").description("board_id of update board")
                        )
                ));
    }

    @Test
    void deleteBoard() throws Exception {
        boardService.delete(boardId);

        Optional<Board> byId = boardService.findById(boardId);
        Assertions.assertTrue(byId.isEmpty());
    }

    @Test
    void findBoardInfo() throws Exception {
        List<BoardReadResponse> boardInfo = boardService.findBoardInfo(boardId);

        Assertions.assertTrue(boardInfo.size() > 0);
    }

    @Test
    void  boardCountPlus() throws Exception {
        int count = boardService.boardCountPlus(boardId);
        Assertions.assertTrue(count > 0);
    }

    @Test
    void saveUploadFile() {
        String text = "file upload test";
        MultipartFile multipartFile = new MockMultipartFile("files", "temp.csv", "text/plain", text.getBytes(StandardCharsets.UTF_8));
        String test = boardService.saveUploadFile(multipartFile);
        System.out.println("test = " + test);
        Assertions.assertNotNull(test);
    }

    @Test
    void newHireByBoardInfo() {
        List<NewHireListResponse> newHireListResponses = boardService.newHireByBoardInfo();
        Assertions.assertTrue(newHireListResponses.size() > 0);
    }

    @Test
    void recommendThisWeek() {
        List<RecommendThisWeekResponse> recommendThisWeekResponses = boardService.recommendThisWeek();
        Assertions.assertTrue(recommendThisWeekResponses.size() > 0);
    }

}
