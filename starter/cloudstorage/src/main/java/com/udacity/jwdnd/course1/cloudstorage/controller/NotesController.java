package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NoteService noteService;
    private final UserService userService;
    private final UserMapper userMapper;

    public NotesController(NoteService noteService, UserService userService, UserMapper userMapper) {
        this.noteService = noteService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/save")
    public String addNewNote(
            Authentication authentication,
            @ModelAttribute("newNote") NoteForm newNote,
            Model model) {

        String userName = authentication.getName();
        int userId = userMapper.getUser(userName).getUserId();

        String noteTitle = newNote.getTitle();
        String noteDescription = newNote.getDescription();
        String noteId = newNote.getId();

        if (noteId.isEmpty()) {
            noteService.createNote(noteTitle, noteDescription, userId);
        } else {
            noteService.updateNote(Integer.parseInt(noteId), noteTitle, noteDescription);
        }

        model.addAttribute("success", true);
        model.addAttribute("tab", "nav-notes-tab");
        model.addAttribute("notes", noteService.getNotesByUser(userId));
        return "home";
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
        model.addAttribute("tab", "nav-notes-tab");
        model.addAttribute("result", "success");

        return "home";
    }

    private int getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }
}