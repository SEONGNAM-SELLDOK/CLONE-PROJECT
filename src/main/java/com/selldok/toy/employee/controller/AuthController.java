package com.selldok.toy.employee.controller;

import com.selldok.toy.employee.model.AuthCallBackRequest;
import com.selldok.toy.employee.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("view")
    public String getEmployeeView() {
        return "login/login.html";
    }

    @PostMapping("check")
    @ResponseBody
    public ResponseEntity auth(@RequestBody AuthCallBackRequest request){
        boolean isResult = authService.validateToken(request.getAuthResponse().getAccessToken());
        boolean isResult2 = authService.checkUserInfo(request.getAuthResponse().getUserId());
        return new ResponseEntity(isResult && isResult2, HttpStatus.OK);
    }

    @GetMapping("callBack")
    public ResponseEntity loginCallback(AuthCallBackRequest authResponse){
        System.out.println(authResponse);
        return new ResponseEntity(HttpStatus.OK);
    }
}
