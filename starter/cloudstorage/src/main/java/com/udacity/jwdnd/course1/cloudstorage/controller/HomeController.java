package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.Attributes;
import com.udacity.jwdnd.course1.cloudstorage.constants.Templates;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class HomeController {

  private final NoteService noteService;

  @GetMapping("/home")
  public String getHome(Principal principal, Note note, Model model) {
    val username = principal.getName();
    model.addAttribute(Attributes.NOTES, noteService.findNotesByUsername(username));
    return Templates.HOME;
  }

  @PostMapping("/home")
  public String postNote(Principal principal, Note note, Model model) {
    val username = principal.getName();
    if (Objects.isNull(note.getNoteId())) {
      noteService.createNoteForUser(note, username);
    } else {
      noteService.updateNote(note);
    }

    model.addAttribute(Attributes.NOTES, noteService.findNotesByUsername(username));
    return Templates.HOME;
  }

  @DeleteMapping("/notes/{id}")
  public String deleteNote(
      Principal principal, @PathVariable("id") String noteId, Note note, Model model) {
    val deletedNoteStatus = noteService.deleteNote(noteId);
    val notes = noteService.findNotesByUsername(principal.getName());
    model.addAttribute(Attributes.NOTES, notes);
    return Templates.HOME;
  }
}
