package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NoteService noteService;
    private final UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public void addNewNote(
            Authentication authentication,
            @ModelAttribute("newNote") NoteForm newNote,
            Model model) {

        String userName = authentication.getName();
        String noteTitle = newNote.getTitle();
        String noteDescription = newNote.getDescription();
        String noteId = newNote.getId();

        if (noteId.isEmpty()) {
            //noteService.createNote(noteTitle, noteDescription, userName);
            System.out.println("Creating NOTE");
            System.out.println("Title: " + noteTitle);
        }
    }

    @GetMapping(value = "/deleteNote/{noteId}")
    public String deleteNote(
            Authentication authentication,
            @PathVariable Integer noteId,
            @ModelAttribute("newNote") NoteForm newNote,
            Model model) {

        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNotesByUser(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    private int getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }
}