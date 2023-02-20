package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SuperDuperDriveController {

    @Autowired
    private NoteService noteService;

    @GetMapping(path = "/home")
    public String getHomePage(Model model) {
        List<Note> notes = noteService.getNotesByCurrentUserId();
        model.addAttribute("notes", notes);
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
}
