package com.selldok.toy.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Incheol Jung
 */
@Controller
public class MainController {
    @GetMapping
    public String getMainView(){
        return "/main/index";
    }
}
