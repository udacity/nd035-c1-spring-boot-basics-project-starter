package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(
            UserService userService,
            NoteService noteService,
            FileService fileService,
            CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Authentication authentication,
                           @ModelAttribute(value = "noteForm") NoteForm noteForm,
                           @ModelAttribute(value = "fileUpload") File fileUpload,
                           @ModelAttribute(value = "credentialForm") Credential credentialForm,
                           Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        model.addAttribute("notesList", this.noteService.getNotesList(userId));
        model.addAttribute("filesList", this.fileService.getFilesList(userId));
        model.addAttribute("credentialsList", this.credentialService.getCredentialListForUser(userId));

        return "home";
    }

}
