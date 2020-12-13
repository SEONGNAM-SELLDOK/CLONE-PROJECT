package com.selldok.toy.company.controller;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.BoardSearchCondition;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.BoardCreateRequest;
import com.selldok.toy.company.model.BoardListResponse;
import com.selldok.toy.company.model.BoardReadResponse;
import com.selldok.toy.company.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Gogisung
 */
@Controller
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private Environment env;

    @GetMapping("/board")
    public String getBoardView() {
        return "work/addwanted.html";
    }

    @GetMapping("/board/read/{id}")
    public ResponseEntity read(@PathVariable("id") Long id) {

        List<BoardReadResponse> read = boardService.findBoard(id);

        // 다른 방법이 있을까?
        List<String> collect1 = read.stream().map(s -> s.getTitle()).collect(Collectors.toList());
        List<String> collect = read.stream()
                .map(s -> new Locale(env.getProperty("path.upload")) + s.getImage())
                .collect(Collectors.toList());
        String image = String.join(" ", collect);
        String title = String.join(" ", collect1);

        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("image", image);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("/board/add")
    public ResponseEntity create(@RequestBody BoardCreateRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Company company = companyRepository.findById(request.getCompanyId()).get();
        System.out.println("company = " + company);

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .endDate(request.getEndDate())
                .company(company)
                .build();

        Long aLong = boardService.create(board);
        return new ResponseEntity(aLong, HttpStatus.CREATED);
    }

    @PostMapping("/board/fileUpload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = boardService.saveUploadFile(file);

        HashMap<String, String> map = new HashMap<>();
        map.put("filename", filename);

        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    // 해당 기업이 작성한 구직 정보 게시글 List 모두 가져오기
    @GetMapping("/board/list")
    public ResponseEntity list(Pageable pageable) {
        Page<BoardListResponse> boardListResponses = boardRepository.searchBoardPage(pageable);
        return new ResponseEntity(boardListResponses, HttpStatus.OK);
    }
}
