package com.selldok.toy.employee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.FaceBookFriend;
import com.selldok.toy.employee.service.AuthService;
import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.service.EventService;
import lombok.AllArgsConstructor;

/**
 * @author Incheol Jung
 */
@Controller
@AllArgsConstructor
public class MainController {

    private final AuthService authService;

    private final EventService eventService;

    @GetMapping
    public String getMainView(Model model){
        List<FaceBookFriend> friends = authService.findFriends();
        model.addAttribute("friends", friends);

        List<Event> events = eventService.getLatest(2);
        model.addAttribute("events", events);
        return "/main/index";
    }
}
