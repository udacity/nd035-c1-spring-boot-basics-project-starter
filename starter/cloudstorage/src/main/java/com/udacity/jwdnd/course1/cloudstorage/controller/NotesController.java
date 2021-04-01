package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotesController {
    private final NotesService notesService;
    private final UserService userService;

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping("/add-note")
    public String addNote(NoteForm noteForm, Authentication authentication, Model model){
        notesService.addNote(noteForm, userService.getLoggedInUserId(authentication));
        setModel(authentication, model);
        return "home";
    }

/*
    @GetMapping("/delete-note")//TODO is there a way to use DeleteMapping with anchor tag?// TODO  Try using the @PathVariable template
    public String deleteNote(Authentication authentication, @ModelAttribute("note") Notes note, Model model){
        notesService.deleteNote(note);
        setModel(authentication, model);
        return "home";
    }*/

    private void setModel(Authentication authentication, Model model) {
        model.addAttribute("notes", notesService.getNotes(userService.getLoggedInUserId(authentication)));
    }
}
