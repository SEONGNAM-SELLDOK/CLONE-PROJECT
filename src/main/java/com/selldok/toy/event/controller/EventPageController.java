package com.selldok.toy.event.controller;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;
import com.selldok.toy.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tetra3
 */
@Controller
@RequestMapping("event")
public class EventPageController {

    @GetMapping("/main")
    public String getCompanyView(){
        return "event/eventlist.html";
    }

}
