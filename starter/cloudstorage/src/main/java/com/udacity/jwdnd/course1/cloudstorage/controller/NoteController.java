package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Message;
import com.udacity.jwdnd.course1.cloudstorage.data.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/add/note")
    public String addNote(NoteForm noteForm, Model model) {
        System.out.println(noteForm);

        Message message = null;
        if (noteForm.getNoteId() == null) {
            message = noteService.addNote(noteForm);
        }

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());
        

        return "result";
    }
}
