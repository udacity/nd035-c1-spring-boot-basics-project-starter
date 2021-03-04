package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/notes")
public class NotesController {
    private UserService userService;
    private NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/{noteId}")
    public String noteAction(@PathVariable("noteId") Integer noteId, @RequestParam(value = "action") String action,
                             Authentication authentication) {
        User currentUser = userService.getUser(authentication.getName());
        Boolean status=false;
        if (action.equals("delete")){
            status=noteService.deleteNote(currentUser.getUserid(), noteId);
        } else {
            return "redirect:/result?error&message=Took a wrong turn there!";
        }
        if (status) {
            return "redirect:/result?success&message=Note successfully deleted!";
        } else {
            return "redirect:/result?error&message=Unable to delete note! Please try again.";
        }
    }

    @PostMapping("/submit")
    public String noteSubmit(Authentication authentication, @ModelAttribute Note note, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        // Check if noteId exists -> create a new one else Coming from edit
        Boolean status =false;
        if (note.getNoteid()==null) {
            Note noteToSubmit = noteService.create(currentUser, note);
            status=noteService.submit(noteToSubmit);
        } else {
            note.setUserid(currentUser.getUserid());
            status=noteService.update(note);
        }
        if (status) {
            return "redirect:/result?success&message=Note successfully submitted!";
        } else {
            return "redirect:/result?error&message=Unable to submit note! Please try again.";
        }
    }
}
