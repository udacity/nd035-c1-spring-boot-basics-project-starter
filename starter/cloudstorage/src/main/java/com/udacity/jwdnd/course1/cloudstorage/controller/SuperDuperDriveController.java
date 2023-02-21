package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SuperDuperDriveController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping(path = "/home")
    public String getHomePage(Model model) {
        List<Note> notes = noteService.getNotesByCurrentUserId();
        List<Credential> credentials = credentialService.getCredentialsByCurrentUserId();
        List<CredentialResponse> credentialResponses = credentialService.buildCredentialsResponses(credentials);

        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentialResponses);
        return "home";
    }

    @PostMapping(path = "/note")
    public String addNote(Model model, @ModelAttribute Note note) {

        if (note.getNoteId() != null) {
            boolean isNoteUpdated = noteService.updateNote(note) != 0;
        } else {
            boolean isNoteAdded = noteService.addNote(note) != 0;
        }
        return "redirect:home";
    }

    @PostMapping(path = "/credential")
    public String addCredential(Model model, @ModelAttribute Credential credential) {

        if (credential.getCredentialId() != null) {
            boolean isCredentialUpdated = true;
        } else {
            boolean isCredentialAdded = credentialService.addCredential(credential) != 0;
        }
        return "redirect:home";
    }
}
