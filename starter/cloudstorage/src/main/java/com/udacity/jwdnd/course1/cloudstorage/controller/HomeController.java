package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final NotesService notesService;
    private final UserService userService;

    public HomeController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(NoteForm noteForm, Authentication authentication, Model model){
        model.addAttribute("notes", notesService.getNotes(userService.getLoggedInUserId(authentication)));
        //model.addAttribute("noteForm", new NoteForm());//TODO see why the parameter in the method doesn't work
        return "home";
    }
}
