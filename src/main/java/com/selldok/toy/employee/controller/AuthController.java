package com.selldok.toy.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Incheol Jung
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping
    public ResponseEntity auth(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
