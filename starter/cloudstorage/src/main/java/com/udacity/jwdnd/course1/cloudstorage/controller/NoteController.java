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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String addNoteForUser(Authentication authentication, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

        if (noteService.isNoteInDatabase(noteForm.getNoteTitle(), noteForm.getNoteDescription())) {
            model.addAttribute("errorMessage", "This note already exists!");
        } else {
            try {
                if (noteForm.getNoteId() == null) {
                    noteService.addNote(userId, noteForm);
                } else {
                    noteService.editNote(userId, noteForm);
                }
                model.addAttribute("successMessage", true);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getLocalizedMessage());
            }
        }

        return "result";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model) {

        try {
            noteService.deleteNote(noteId);
            if (noteService.getNoteById(noteId) == null) {
                model.addAttribute("successMessage", true);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }

}
