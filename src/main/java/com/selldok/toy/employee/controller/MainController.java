package com.selldok.toy.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.selldok.toy.employee.model.EmployeeProfileResponse;
import com.selldok.toy.employee.model.FaceBookFriend;
import com.selldok.toy.employee.service.AuthService;
import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.service.EventService;
import lombok.AllArgsConstructor;

/**
 * @author Incheol Jung, Go Gisung
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;

    @GetMapping
    public String getMainView(Model model){
        List<FaceBookFriend> friends = authService.findFriends();
        model.addAttribute("friends", friends);

        List<Event> events = eventService.getLatest(2);
        model.addAttribute("events", events);
        return "/main/index";
    }

    @GetMapping("newHire")
    public ResponseEntity<String> newHireByBoardInfo() {
        List<NewHireListResponse> newHire = boardService.newHireByBoardInfo();
        return new ResponseEntity(newHire, HttpStatus.OK);
    }
}
