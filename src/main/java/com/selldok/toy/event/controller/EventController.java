package com.selldok.toy.event.controller;

import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;
import com.selldok.toy.event.service.EventService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Optional<Event> eventOptional = eventService.findById(id);
        model.addAttribute("event", eventOptional.orElse(null));
        return "event/eventedit";
    }

    @GetMapping("list")
    public String listPage(Model model) {
        model.addAttribute("events", eventService.getList(0l));
        return "event/eventlist";
    }

    @GetMapping("search")
    public String searchPage(Model model) {
        model.addAttribute("events", eventService.getList(0l));
        return "event/eventlistForjobseeker";
    }

    @GetMapping("detail/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
        // Optional<Event> eventOptional = eventService.findById(id);
        // model.addAttribute("event", eventOptional.orElse(null));
        return "event/eventdetail";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<List<Event>> get(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getList(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity insert(@RequestBody InsertEventRequest request) {
        eventService.insert(request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UpdateEventRequest request) {
        eventService.update(id, request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        eventService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
