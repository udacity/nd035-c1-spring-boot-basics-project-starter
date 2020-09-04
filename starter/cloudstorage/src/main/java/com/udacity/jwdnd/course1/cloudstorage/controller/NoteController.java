package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Message;
import com.udacity.jwdnd.course1.cloudstorage.data.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        } else {
            message = noteService.editNote(noteForm);
        }

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());
        

        return "result";
    }

    @GetMapping("/delete/note")
    public String deleteNote(@RequestParam("id") Integer id, Model model) {

        Message message = noteService.deleteNote(id);

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());

        return "result";
    }
}
