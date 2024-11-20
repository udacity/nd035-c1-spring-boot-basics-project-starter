package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        note.setUserid(userId.intValue());

        try {
            noteService.addNote(note);
            model.addAttribute("successMessage", "Note successfully created!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error creating the note. Please try again.");
        }
        return "redirect:/home";
    }

    @PostMapping("/update")
    public String updateNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        String username = authentication.getName();
        try {
            noteService.updateNote(note);
            model.addAttribute("successMessage", "Note successfully updated!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error updating the note. Please try again.");
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("successMessage", "Note successfully deleted!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error deleting the note. Please try again.");
        }
        return "redirect:/home";
    }
}
