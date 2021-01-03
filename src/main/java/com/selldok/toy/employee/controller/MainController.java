package com.selldok.toy.employee.controller;

import com.selldok.toy.company.model.BoardReadResponse;
import com.selldok.toy.company.model.NewHireListResponse;
import com.selldok.toy.company.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Incheol Jung, Go Gisung
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;

    @GetMapping
    public String getMainView(){
        return "/main/index";
    }

    @GetMapping("newHire")
    public ResponseEntity<String> newHireByBoardInfo() {
        List<NewHireListResponse> newHire = boardService.newHireByBoardInfo();
        return new ResponseEntity(newHire, HttpStatus.OK);
    }
}
