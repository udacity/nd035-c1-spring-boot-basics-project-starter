package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping(value = "/result")
    public String resultView(){
        return "result";
    }

    @GetMapping(value = {"/", "/home"})
    public String homeView(Authentication authentication, Note note, File file, Credential credential, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        model.addAttribute(currentUser);
        model.addAttribute("files", fileService.getFiles(currentUser));
        model.addAttribute("notes", noteService.getNotes(currentUser));
        model.addAttribute("credentials",credentialService.getCredentials(currentUser));
        return "home";
    }

}
