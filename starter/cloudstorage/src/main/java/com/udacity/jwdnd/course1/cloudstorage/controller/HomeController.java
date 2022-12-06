package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService notesService;
    private final UserService userService;
    private final CredentialService credentialService;

    public HomeController(
            FileService fileService,
            NoteService notesService,
            UserService userService,
            CredentialService credentialService) {
        this.fileService = fileService;
        this.notesService = notesService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String displayHome(
            Authentication auth,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("credentialForm") CredentialForm credentialForm,
            Model model) {

        if (userService.getUser(auth.getName()) != null) {
            int id = userService.getLoggedInUserId(auth);
            model.addAttribute("fileList", fileService.getAllFiles(id));
            model.addAttribute("notes", notesService.getNotesByUser(id));
            model.addAttribute("credentials", credentialService.getUserCredentials(id));
            return "home";
        }

        return "login";
    }

    @PostMapping("/delete")
    public String fileDelete() {
        return "home";
    }
}