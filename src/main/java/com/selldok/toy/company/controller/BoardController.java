package com.selldok.toy.company.controller;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.BoardSearchCondition;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.*;
import com.selldok.toy.company.service.BoardService;

import com.selldok.toy.company.service.WdlistService;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;
/**
 * @author Gogisung
 */
@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CompanyRepository companyRepository;

    private final WdlistService wdlistService;

    private final EmployeeService employeeService;


    @GetMapping
    public String getBoardView() {
        return "board/add";
    }

    @GetMapping("read")
    public String getBoardRead() { return "board/read"; }

    @GetMapping("list")
    public String getBoardList() { return "board/list"; }

    @GetMapping("{id}")
    public ResponseEntity<List<BoardReadResponse>> read(@PathVariable("id") Long id) {
        List<BoardReadResponse> boardInfo = boardService.findBoard(id);
        return new ResponseEntity(boardInfo, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<String> create(final @Valid @RequestBody BoardCreateRequest request) {

        Company company = companyRepository.findById(request.getCompanyId()).get();
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .endDate(request.getEndDate())
                .company(company)
                .build();

        Long boardId = boardService.create(board);

        HashMap<String, Long> map = new HashMap<>();
        map.put("board_id", boardId);

        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody BoardUpdateRequest request) {
        Long boardId = boardService.update(id, request);

        HashMap<String, Long> map = new HashMap<>();
        map.put("board_id", boardId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("fileUpload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = boardService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    // 해당 기업이 작성한 구직 정보 게시글 List 모두 가져오기
    @GetMapping("/boards")
    public ResponseEntity list(BoardSearchCondition condition, Pageable pageable) {
        Page<BoardListResponse> boardListResponses = boardRepository.searchBoard(condition, pageable);
        return new ResponseEntity(boardListResponses, HttpStatus.OK);
    }

    @PostMapping("countPlus")
    public ResponseEntity boardCountPlus(final @RequestBody BoardCountPlusRequest request) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.get(request.getEmployeeId()));

        int count = 0;
        if (employee.isPresent()) {
            count = boardService.boardCountPlus(request.getBoardId());
            long start = System.currentTimeMillis();
            log.info(request.getBoardId() + " 글의 Cache 수행 시작 시간: " + Long.toString(start) );
        }
        return new ResponseEntity(count, HttpStatus.OK);
    }

    /**
     * wdlist에 쓰일 부분입니다.
     */
    @ResponseBody
    @PostMapping("/sync")
    public String getDataFromWanted() {
        wdlistService.syncWithWanted();
        return "success";
    }
}
