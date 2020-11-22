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
 * @author HJ Lee
 */
@Controller
@RequestMapping("event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<List<Employee>> get(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getList(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity insert(@RequestBody InsertEventRequest request) {
        eventService.insert(request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UpdateEventRequest request) {
        eventService.update(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        eventService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
