package com.selldok.toy.event.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.model.EventSearchRequest;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;
import com.selldok.toy.event.service.EventService;

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
	public String listPage(Model model, Principal principal, EventSearchRequest request) {
		request.setOwner(principal.getName());
		model.addAttribute("events", eventService.getList(request));
		return "event/eventlist";
	}

	@GetMapping("search")
	public String searchPage(Model model, EventSearchRequest request) {
		model.addAttribute("events", eventService.getList(request));
		return "event/eventlistForjobseeker";
	}

	@GetMapping("detail/{id}")
	public String detailPage(@PathVariable("id") Long id, Model model) {
		Optional<Event> eventOptional = eventService.findById(id);
		model.addAttribute("event", eventOptional.orElse(null));
		return "event/eventdetail";
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, headers = ("Content-Type=application/x-www-form-urlencoded"))
	@ResponseBody
	public ResponseEntity insert(HttpServletRequest servletRequest,
		@RequestParam(value = "image", required = false) MultipartFile file,
		InsertEventRequest request) {
		eventService.insert(servletRequest, file, request);
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
