package com.selldok.toy.company.controller;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.model.BoardCreateRequest;
import com.selldok.toy.company.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/work/addwanted")
    public String getCompanyView(){
        return "work/addwanted.html";
    }

    @PostMapping("/board/add")
    public ResponseEntity create(@RequestBody BoardCreateRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .endDate(request.getEndDate())
                .build();

        boardService.create(board);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/board/fileUpload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = boardService.saveUploadFile(file);

        HashMap<String, String> map = new HashMap<>();
        map.put("filename", filename);

        return new ResponseEntity(map, HttpStatus.CREATED);
    }


    @GetMapping("/board/list")
    public ResponseEntity list() {
        return new ResponseEntity(boardService.findAllBoard(), HttpStatus.OK);
    }
}
