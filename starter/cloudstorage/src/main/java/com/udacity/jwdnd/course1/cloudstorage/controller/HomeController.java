package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    public HomeController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        int userId = userService.getUserId(authentication.getName());
        model.addAttribute("notesList", this.noteService.getNotesList(userId));
        return "home";
    }

    @PostMapping("note")
    public String addNoteForUser(Authentication authentication, NoteForm noteForm, Model model) {
        int userId = userService.getUserId(authentication.getName());
        this.noteService.addNote(userId, noteForm);
        return "result";
    }

}
