package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;

    public HomeController(
            UserService userService,
            NoteService noteService,
            FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Authentication authentication,
                           @ModelAttribute(value = "noteForm") NoteForm noteForm,
                           @ModelAttribute(value = "fileUpload") File fileUpload,
                           Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        model.addAttribute("notesList", this.noteService.getNotesList(userId));
        model.addAttribute("filesList", this.fileService.getFilesList(userId));

        return "home";
    }

}
