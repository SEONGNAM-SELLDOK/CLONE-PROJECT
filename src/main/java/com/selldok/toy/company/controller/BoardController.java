package com.selldok.toy.company.controller;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.BoardSearchCondition;
import com.selldok.toy.company.dao.CompanyRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import com.selldok.toy.company.model.*;
import com.selldok.toy.company.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
/**
 * @author Gogisung
 */
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
@PropertySource(value = "classpath:/option.properties")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CompanyRepository companyRepository;

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
    public ResponseEntity create(@RequestBody BoardCreateRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
        boardService.update(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
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
}
